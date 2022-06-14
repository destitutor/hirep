package net.hexabrain.hireo.account.controller.dto.mapper;

import net.hexabrain.hireo.web.account.domain.Account;
import net.hexabrain.hireo.web.account.domain.AccountType;
import net.hexabrain.hireo.web.account.domain.Employer;
import net.hexabrain.hireo.web.account.domain.Freelancer;
import net.hexabrain.hireo.web.account.dto.RegisterRequest;
import net.hexabrain.hireo.web.account.dto.mapper.AccountMapper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AccountMapperTest {

    @Autowired
    AccountMapper mapper;

    @ParameterizedTest
    @MethodSource
    void resolve(AccountType dtoType, Class<?> actualType) {
        RegisterRequest dto = new RegisterRequest(
                "test@test.com", "testtest", "testtest", dtoType, "asd"
        );
        Account mappedAccount = mapper.toEntity(dto);

        assertThat(mappedAccount).isExactlyInstanceOf(actualType);
        assertThat(mappedAccount.getEmail()).isEqualTo(dto.getEmail());
        assertThat(mappedAccount.getPassword()).isEqualTo(dto.getPassword());
        assertThat(mappedAccount.getType()).isEqualTo(dto.getType());
    }

    private static Stream<Arguments> resolve() {
        return Stream.of(
                Arguments.of(AccountType.EMPLOYER, Employer.class),
                Arguments.of(AccountType.FREELANCER, Freelancer.class)
        );
    }
}