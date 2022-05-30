package net.hexabrain.hireo.service;

import lombok.RequiredArgsConstructor;
import net.hexabrain.hireo.domain.Account;
import net.hexabrain.hireo.repository.AccountRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

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
}
