package net.hexabrain.hireo.web.account.repository;

import net.hexabrain.hireo.web.account.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByEmail(String email);
}
