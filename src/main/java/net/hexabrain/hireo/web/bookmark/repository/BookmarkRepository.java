package net.hexabrain.hireo.web.bookmark.repository;

import net.hexabrain.hireo.web.bookmark.domain.Bookmark;
import net.hexabrain.hireo.web.common.exception.company.BookmarkNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    Optional<Bookmark> findByAccountIdAndCompanyId(Long accountId, Long companyId);
    Optional<Bookmark> findByAccountIdAndJobId(Long accountId, Long jobId);

    boolean existsByAccountIdAndCompanyId(Long accountId, Long companyId);
    boolean existsByAccountIdAndJobId(Long accountId, Long companyId);

    @Query("SELECT b FROM Bookmark b JOIN b.company WHERE b.account.email = :email")
    Page<Bookmark> findAllCompanyBookmarks(@Param("email") String email, Pageable pageable);

    default Bookmark findByAccountIdAndCompanyIdOrThrow(Long accountId, Long companyId) {
        return findByAccountIdAndCompanyId(accountId, companyId)
                .orElseThrow(BookmarkNotFoundException::new);
    }

    default Bookmark findByAccountIdAndJobIdOrThrow(Long accountId, Long jobId) {
        return findByAccountIdAndJobId(accountId, jobId)
                .orElseThrow(BookmarkNotFoundException::new);
    }
}
