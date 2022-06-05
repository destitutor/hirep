package net.hexabrain.hireo.account.service;

import net.hexabrain.hireo.web.account.domain.Account;
import net.hexabrain.hireo.web.account.domain.AccountType;
import net.hexabrain.hireo.web.account.domain.Employer;
import net.hexabrain.hireo.web.account.repository.AccountRepository;
import net.hexabrain.hireo.web.account.service.AccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {
    @Mock
    AccountRepository accountRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    AccountService accountService;

    @Test
    void save() {
        Account expectedAccount = new Employer("test@test.com", "test1234", AccountType.EMPLOYER);

        String encodedPassword = "encoded password";
        when(passwordEncoder.encode(expectedAccount.getPassword())).thenReturn(encodedPassword);
        when(accountRepository.save(any(Account.class))).thenReturn(expectedAccount);

        Account actualAccount = accountService.save(expectedAccount);

        assertThat(actualAccount.getPassword()).isEqualTo(encodedPassword);
        assertThat(actualAccount.getEmail()).isEqualTo(expectedAccount.getEmail());
        assertThat(actualAccount.getType()).isEqualTo(expectedAccount.getType());

        verify(passwordEncoder).encode(anyString());
        verify(accountRepository).save(any(Account.class));
    }
}