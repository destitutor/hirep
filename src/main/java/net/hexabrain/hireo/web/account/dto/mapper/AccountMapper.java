package net.hexabrain.hireo.web.account.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ObjectFactory;

import net.hexabrain.hireo.web.account.domain.Account;
import net.hexabrain.hireo.web.account.domain.AccountType;
import net.hexabrain.hireo.web.account.domain.Employer;
import net.hexabrain.hireo.web.account.domain.Freelancer;
import net.hexabrain.hireo.web.account.dto.RegisterRequest;
import net.hexabrain.hireo.web.common.mapper.BaseMapper;

@Mapper(componentModel = "spring")
public interface AccountMapper extends BaseMapper<RegisterRequest, Account> {

    @Mapping(target = "name", source = "profile.name")
    @Mapping(target = "passwordConfirm", ignore = true)
    RegisterRequest toDto(Account e);


    @ObjectFactory
    default Account resolve(RegisterRequest dto) {
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