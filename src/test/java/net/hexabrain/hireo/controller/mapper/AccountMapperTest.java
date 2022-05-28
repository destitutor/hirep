package net.hexabrain.hireo.controller.mapper;

import net.hexabrain.hireo.transport.mapper.AccountMapper;
import net.hexabrain.hireo.transport.dto.AccountDto;
import net.hexabrain.hireo.domain.Account;
import net.hexabrain.hireo.domain.AccountType;
import net.hexabrain.hireo.domain.Employer;
import net.hexabrain.hireo.domain.Freelancer;
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
        AccountDto dto = new AccountDto(
                "test@test.com", "testtest", "testtest", dtoType
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