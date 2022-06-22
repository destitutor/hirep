package net.hexabrain.hireo.web.company.dto.mapper;

import org.mapstruct.Mapper;

import net.hexabrain.hireo.web.common.mapper.BaseMapper;
import net.hexabrain.hireo.web.company.domain.Company;
import net.hexabrain.hireo.web.company.dto.CompanyInfoResponse;

@Mapper(componentModel = "spring", uses = { AddressMapper.class })
public interface CompanyInfoMapper extends BaseMapper<Company, CompanyInfoResponse> {
}
