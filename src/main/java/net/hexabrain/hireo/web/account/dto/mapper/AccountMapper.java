package net.hexabrain.hireo.web.account.dto.mapper;

import org.mapstruct.Mapper;

import net.hexabrain.hireo.web.account.domain.Account;
import net.hexabrain.hireo.web.account.dto.AccountDto;

@Mapper(componentModel = "spring", uses = {ProfileResponseMapper.class})
public interface AccountMapper {
	AccountDto toDto(Account e);
}
