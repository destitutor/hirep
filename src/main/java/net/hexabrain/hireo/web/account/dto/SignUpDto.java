package net.hexabrain.hireo.web.account.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import net.hexabrain.hireo.web.account.domain.AccountType;
import net.hexabrain.hireo.web.common.validation.constraints.FieldMatch;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@FieldMatch(first = "password", second = "passwordConfirm")
public class SignUpDto {
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
