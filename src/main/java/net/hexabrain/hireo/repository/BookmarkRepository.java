package net.hexabrain.hireo.repository;

import net.hexabrain.hireo.domain.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    Bookmark findByCompanyIdAndAccountId(Long companyId, Long accountId);

    boolean existsByCompanyIdAndAccountId(Long companyId, Long accountId);
}
