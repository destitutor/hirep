package net.hexabrain.hireo.web.account.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import net.hexabrain.hireo.web.account.domain.Account;
import net.hexabrain.hireo.web.account.dto.AccountDto;
import net.hexabrain.hireo.web.account.dto.mapper.AccountMapper;
import net.hexabrain.hireo.web.account.repository.AccountRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {
    @PersistenceContext
    EntityManager entityManager;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccountMapper accountMapper;

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

    public AccountDto findByEmail(String email) {
        Account foundAccount = accountRepository.findByEmailOrThrow(email);
        return accountMapper.toDto(foundAccount);
    }

    public boolean isExist(Long id) {
        return accountRepository.existsById(id);
    }

    public long count() {
        return accountRepository.count();
    }
}
