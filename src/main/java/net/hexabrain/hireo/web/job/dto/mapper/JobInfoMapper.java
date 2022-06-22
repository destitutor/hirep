package net.hexabrain.hireo.web.job.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import net.hexabrain.hireo.web.common.mapper.BaseMapper;
import net.hexabrain.hireo.web.company.dto.mapper.AddressMapper;
import net.hexabrain.hireo.web.company.dto.mapper.CompanyDetailsMapper;
import net.hexabrain.hireo.web.job.domain.Job;
import net.hexabrain.hireo.web.job.dto.JobInfoResponse;

@Mapper(componentModel = "spring", uses = {AddressMapper.class, CompanyDetailsMapper.class})
public interface JobInfoMapper extends BaseMapper<JobInfoResponse, Job> {
	@Mapping(source = "createdDate", target = "postedAt")
	JobInfoResponse toDto(Job job);
}