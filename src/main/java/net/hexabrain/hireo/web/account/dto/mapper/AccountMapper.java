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
            return Employer.builder()
                    .email(dto.getEmail())
                    .password(dto.getPassword())
                    .type(dto.getType())
                    .build();
        } else if (dto.getType().equals(AccountType.FREELANCER)) {
            return Freelancer.builder()
                    .email(dto.getEmail())
                    .password(dto.getPassword())
                    .type(dto.getType())
                    .build();
        } else {
            throw new IllegalArgumentException("Invalid account type");
        }
    }
}