package net.hexabrain.hireo.web.bookmark.service;

import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.hexabrain.hireo.web.account.domain.Account;
import net.hexabrain.hireo.web.account.repository.AccountRepository;
import net.hexabrain.hireo.web.bookmark.domain.Bookmark;
import net.hexabrain.hireo.web.bookmark.dto.BookmarkedCompanyResponse;
import net.hexabrain.hireo.web.bookmark.dto.mapper.BookmarkMapper;
import net.hexabrain.hireo.web.bookmark.repository.BookmarkRepository;
import net.hexabrain.hireo.web.common.exception.bookmark.AlreadyBookmarkedException;
import net.hexabrain.hireo.web.company.domain.Company;
import net.hexabrain.hireo.web.company.repository.CompanyRepository;
import net.hexabrain.hireo.web.job.domain.Job;
import net.hexabrain.hireo.web.job.repository.JobRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class BookmarkService {
    private final BookmarkRepository bookmarkRepository;
    private final CompanyRepository companyRepository;
    private final JobRepository jobRepository;
    private final AccountRepository accountRepository;
    private final BookmarkMapper bookmarkMapper;

    public Page<BookmarkedCompanyResponse> getCompanyBookmarks(User user, Pageable pageable) {
        Page<Bookmark> bookmarks = bookmarkRepository.findAllCompanyBookmarks(user.getUsername(), pageable);
        return new PageImpl<>(
            bookmarks.stream()
                .map(bookmarkMapper::toDto)
                .collect(Collectors.toList())
            , pageable, bookmarks.getTotalElements()
        );
    }

    public Long addToCompany(User user, Long companyId) {
        Company company = companyRepository.findByIdOrThrow(companyId);
        Account loggedInUser = accountRepository.findByEmailOrThrow(user.getUsername());
        Bookmark bookmark = new Bookmark(loggedInUser, company);
        try {
            return bookmarkRepository.save(bookmark).getId();
        } catch (DataIntegrityViolationException ex) {
            throw new AlreadyBookmarkedException();
        }
    }

    public Long addToJob(User user, Long jobId) {
        Job job = jobRepository.findByIdOrThrow(jobId);
        Account loggedInUser = accountRepository.findByEmailOrThrow(user.getUsername());
        Bookmark bookmark = new Bookmark(loggedInUser, job);
        try {
            return bookmarkRepository.save(bookmark).getId();
        } catch (DataIntegrityViolationException ex) {
            throw new AlreadyBookmarkedException();
        }
    }

    public void deleteOnJob(User user, Long jobId) {
        Account loggedInUser = accountRepository.findByEmailOrThrow(user.getUsername());
        Bookmark foundBookmark = bookmarkRepository.findByAccountIdAndJobIdOrThrow(loggedInUser.getId(), jobId);
        bookmarkRepository.delete(foundBookmark);
    }

    public void deleteOnCompany(User user, Long companyId) {
        Account loggedInUser = accountRepository.findByEmailOrThrow(user.getUsername());
        Bookmark foundBookmark = bookmarkRepository.findByAccountIdAndCompanyIdOrThrow(loggedInUser.getId(), companyId);
        bookmarkRepository.delete(foundBookmark);
    }

    @Transactional(readOnly = true)
    public boolean isJobBookmarked(User user, Long jobId) {
        Account loggedInUser = accountRepository.findByEmailOrThrow(user.getUsername());
        return bookmarkRepository.existsByAccountIdAndJobId(loggedInUser.getId(), jobId);
    }

    @Transactional(readOnly = true)
    public boolean isCompanyBookmarked(User user, Long companyId) {
        Account loggedInUser = accountRepository.findByEmailOrThrow(user.getUsername());
        return bookmarkRepository.existsByAccountIdAndCompanyId(loggedInUser.getId(), companyId);
    }
}
