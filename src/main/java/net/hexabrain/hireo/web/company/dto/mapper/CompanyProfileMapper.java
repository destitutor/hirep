package net.hexabrain.hireo.web.company.dto.mapper;

import org.mapstruct.Mapper;

import net.hexabrain.hireo.web.common.mapper.BaseMapper;
import net.hexabrain.hireo.web.company.domain.Company;
import net.hexabrain.hireo.web.company.dto.CompanyProfileResponse;
import net.hexabrain.hireo.web.review.dto.mapper.ReviewResponseMapper;

@Mapper(componentModel = "spring", uses = { AddressResponseMapper.class, ReviewResponseMapper.class})
public interface CompanyProfileMapper extends BaseMapper<CompanyProfileResponse, Company> {
}
