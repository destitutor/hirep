package net.hexabrain.hireo.api.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class LoginRequest {
    @NotEmpty
    @Email
    @Length(max = 32)
    private String email;

    @NotEmpty
    @Length(min = 8, max = 20)
    private String password;
}
