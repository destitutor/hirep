package net.hexabrain.hireo.web.account.dto;

import net.hexabrain.hireo.web.account.domain.AccountType;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountDto {
	private Long id;

	private String email;

	private AccountType type;

	private ProfileDto profile;
}
