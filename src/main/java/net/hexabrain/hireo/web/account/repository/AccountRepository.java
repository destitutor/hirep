package net.hexabrain.hireo.web.account.repository;

import net.hexabrain.hireo.web.account.domain.Account;
import net.hexabrain.hireo.web.common.exception.company.AccountNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByEmail(String email);

    default Account findByEmailOrThrow(String email) {
        return findByEmail(email)
                .orElseThrow(AccountNotFoundException::new);
    }
}
