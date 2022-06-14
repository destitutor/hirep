package net.hexabrain.hireo.web.account.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ObjectFactory;
import org.mapstruct.ReportingPolicy;

import net.hexabrain.hireo.web.account.domain.Account;
import net.hexabrain.hireo.web.account.domain.AccountType;
import net.hexabrain.hireo.web.account.domain.Employer;
import net.hexabrain.hireo.web.account.domain.Freelancer;
import net.hexabrain.hireo.web.account.dto.SignUpDto;
import net.hexabrain.hireo.web.common.mapper.BaseMapper;

@Mapper(componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    uses = {ProfileResponseMapper.class})
public interface SignUpMapper extends BaseMapper<SignUpDto, Account> {

    Account toEntity(SignUpDto dto);

    SignUpDto toDto(Account e);

    @ObjectFactory
    default Account resolve(SignUpDto dto) {
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