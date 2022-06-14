package net.hexabrain.hireo.web.company.dto.mapper;

import org.mapstruct.Mapper;

import net.hexabrain.hireo.web.common.mapper.BaseMapper;
import net.hexabrain.hireo.web.company.domain.Company;
import net.hexabrain.hireo.web.company.dto.CompanyResponse;

@Mapper(componentModel = "spring", uses = { AddressResponseMapper.class })
public interface CompanyResponseMapper extends BaseMapper<Company, CompanyResponse> {
}
