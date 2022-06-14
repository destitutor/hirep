package net.hexabrain.hireo.account.service;

import net.hexabrain.hireo.web.account.domain.Account;
import net.hexabrain.hireo.web.account.domain.AccountType;
import net.hexabrain.hireo.web.account.domain.Employer;
import net.hexabrain.hireo.web.account.domain.Profile;
import net.hexabrain.hireo.web.account.dto.AccountDto;
import net.hexabrain.hireo.web.account.repository.AccountRepository;
import net.hexabrain.hireo.web.account.service.AccountService;
import net.hexabrain.hireo.web.common.exception.company.AccountNotFoundException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.*;
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
    @DisplayName("존재하지 않는 이메일을 찾으려고 하면 예외를 던짐")
    void findAccountByNonExistingEmail() {
        when(accountRepository.findByEmailOrThrow(any())).thenThrow(AccountNotFoundException.class);

        assertThatThrownBy(() -> accountService.findByEmail("non_existing@email.com"))
            .isInstanceOf(AccountNotFoundException.class);
    }

    @Test
    @DisplayName("존재하는 이메일을 찾으려고 하면 정상 반환")
    void findAccountByExistingEmail() {
        String input = "existing@email.com";
        Account expected = new Employer(input, "password", AccountType.EMPLOYER, new Profile("empty"));

        when(accountRepository.findByEmailOrThrow(any())).thenReturn(expected);

        AccountDto foundAccount = accountService.findByEmail(input);

        // assertThat(foundAccount).isEqualTo(expectedDto);
    }

    @Test
    void save() {
        Account expectedAccount = new Employer("test@test.com", "test1234", AccountType.EMPLOYER, null);

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