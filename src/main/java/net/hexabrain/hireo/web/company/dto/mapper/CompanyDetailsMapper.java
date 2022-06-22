package net.hexabrain.hireo.web.company.dto.mapper;

import org.mapstruct.Mapper;

import net.hexabrain.hireo.web.common.mapper.BaseMapper;
import net.hexabrain.hireo.web.company.domain.Company;
import net.hexabrain.hireo.web.company.dto.CompanyDetailsResponse;
import net.hexabrain.hireo.web.review.dto.mapper.ReviewResponseMapper;

@Mapper(componentModel = "spring", uses = { AddressMapper.class, ReviewResponseMapper.class})
public interface CompanyDetailsMapper extends BaseMapper<CompanyDetailsResponse, Company> {
}
