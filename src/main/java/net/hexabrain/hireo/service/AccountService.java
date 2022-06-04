package net.hexabrain.hireo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hexabrain.hireo.domain.Account;
import net.hexabrain.hireo.repository.AccountRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(email);
        if (Objects.isNull(account)) {
            throw new UsernameNotFoundException(email);
        }
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
        return accountRepository.findById(id).get();
    }

    public Account findOne(String email) {
        return accountRepository.findByEmail(email);
    }

    public boolean isExist(Long id) {
        return accountRepository.existsById(id);
    }

    public Account getCurrentAccount() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("principal {}", principal);
        if (principal instanceof UserDetails) {
            return findOne(((UserDetails) principal).getUsername());
        } else {
            return findOne(principal.toString());
        }
    }
}
