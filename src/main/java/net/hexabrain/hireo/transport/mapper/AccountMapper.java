package net.hexabrain.hireo.transport.mapper;

import net.hexabrain.hireo.transport.dto.AccountDto;
import net.hexabrain.hireo.domain.Account;
import net.hexabrain.hireo.domain.AccountType;
import net.hexabrain.hireo.domain.Employer;
import net.hexabrain.hireo.domain.Freelancer;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AccountMapper extends BaseMapper<AccountDto, Account> {
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