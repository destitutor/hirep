package net.hexabrain.hireo.web.job.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import net.hexabrain.hireo.web.common.mapper.BaseMapper;
import net.hexabrain.hireo.web.company.dto.mapper.AddressMapper;
import net.hexabrain.hireo.web.company.dto.mapper.CompanyDetailsMapper;
import net.hexabrain.hireo.web.job.domain.Job;
import net.hexabrain.hireo.web.job.dto.JobDetailsResponse;

@Mapper(componentModel = "spring", uses = {AddressMapper.class, CompanyDetailsMapper.class})
public interface JobDetailsMapper extends BaseMapper<JobDetailsResponse, Job> {
	@Mapping(source = "createdDate", target = "postedAt")
	JobDetailsResponse toDto(Job job);
}