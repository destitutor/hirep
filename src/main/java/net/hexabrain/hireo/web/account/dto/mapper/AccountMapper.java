package net.hexabrain.hireo.web.account.dto.mapper;

import net.hexabrain.hireo.web.account.domain.Account;
import net.hexabrain.hireo.web.account.domain.AccountType;
import net.hexabrain.hireo.web.account.domain.Employer;
import net.hexabrain.hireo.web.account.domain.Freelancer;
import net.hexabrain.hireo.web.account.dto.AccountDto;
import net.hexabrain.hireo.web.common.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ObjectFactory;

@Mapper(componentModel = "spring")
public interface AccountMapper extends BaseMapper<AccountDto, Account> {

    @Mapping(target = "name", source = "profile.name")
    @Mapping(target = "passwordConfirm", ignore = true)
    AccountDto toDto(Account e);


    @ObjectFactory
    default Account resolve(AccountDto dto) {
        if (dto.getType().equals(AccountType.EMPLOYER)) {
            return new Employer(dto.getEmail(), dto.getPassword(), dto.getType());
        } else if (dto.getType().equals(AccountType.FREELANCER)) {
            return new Freelancer(dto.getEmail(), dto.getPassword(), dto.getType());
        } else {
            throw new IllegalArgumentException("Invalid account type");
        }
    }
}