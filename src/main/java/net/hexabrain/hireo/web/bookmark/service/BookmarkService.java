package net.hexabrain.hireo.web.bookmark.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hexabrain.hireo.web.company.service.CompanyService;
import net.hexabrain.hireo.web.account.domain.Account;
import net.hexabrain.hireo.web.bookmark.domain.Bookmark;
import net.hexabrain.hireo.web.company.domain.Company;
import net.hexabrain.hireo.web.bookmark.repository.BookmarkRepository;
import net.hexabrain.hireo.web.account.service.AccountService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookmarkService {
    private final BookmarkRepository bookmarkRepository;
    private final CompanyService companyService;
    private final AccountService accountService;

    public Long post(Long companyId) {
        UserDetails currentUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Company company = companyService.findOne(companyId);
        Account loggedInUser = accountService.findOne(currentUser.getUsername());

        Bookmark bookmark = new Bookmark(company, loggedInUser);
        return bookmarkRepository.save(bookmark).getId();
    }

    public void delete(Long companyId) {
        UserDetails currentUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account loggedInUser = accountService.findOne(currentUser.getUsername());

        Bookmark foundBookmark = bookmarkRepository.findByCompanyIdAndAccountId(companyId, loggedInUser.getId());
        bookmarkRepository.delete(foundBookmark);
    }

    public boolean isExist(Long companyId) {
        UserDetails currentUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account loggedInUser = accountService.findOne(currentUser.getUsername());
        return bookmarkRepository.existsByCompanyIdAndAccountId(companyId, loggedInUser.getId());
    }
}
