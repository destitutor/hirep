package net.hexabrain.hireo.transport.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.hexabrain.hireo.domain.AccountType;
import net.hexabrain.hireo.transport.validation.constraints.FieldMatch;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor @AllArgsConstructor
@FieldMatch(first = "password", second = "passwordConfirm")
public class AccountDto {
    @NotEmpty
    @Email
    @Length(max = 32)
    private String email;

    @NotEmpty
    @Length(min = 8, max = 20)
    private String password;

    @NotEmpty
    private String passwordConfirm;

    @NotNull
    private AccountType type;

    private String name;
}
