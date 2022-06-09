package net.hexabrain.hireo.web.account.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import net.hexabrain.hireo.web.account.domain.Account;
import net.hexabrain.hireo.web.account.domain.QAccount;
import net.hexabrain.hireo.web.account.repository.AccountRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {
    @PersistenceContext
    EntityManager entityManager;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));
        return User.builder()
                .username(email)
                .password(account.getPassword())
                .roles(account.getType().toString())
                .build();
    }

    public Account save(Account account) {
        account.encodePassword(passwordEncoder);
        return this.accountRepository.save(account);
    }

    public Account findOne(Long id) {
        JPAQueryFactory query = new JPAQueryFactory(entityManager);
        QAccount account = QAccount.account;
        return query
                .selectFrom(account)
                .where(account.id.eq(id))
                .join(account.reviews).fetchJoin()
                .join(account.bookmarks).fetchJoin()
                .fetchOne();
    }

    public Account findOne(String email) {
        return accountRepository.findByEmailOrThrow(email);
    }

    public boolean isExist(Long id) {
        return accountRepository.existsById(id);
    }

    public long count() {
        return accountRepository.count();
    }

    public Account getCurrentAccount() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return findOne(((UserDetails) principal).getUsername());
        } else {
            return findOne(principal.toString());
        }
    }
}
