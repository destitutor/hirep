package net.hexabrain.hireo.web.job.dto.mapper;

import org.mapstruct.Mapper;

import net.hexabrain.hireo.web.common.mapper.BaseMapper;
import net.hexabrain.hireo.web.company.dto.mapper.AddressResponseMapper;
import net.hexabrain.hireo.web.company.dto.mapper.CompanyProfileMapper;
import net.hexabrain.hireo.web.job.domain.Job;
import net.hexabrain.hireo.web.job.dto.JobDto;

@Mapper(componentModel = "spring", uses = {AddressResponseMapper.class, CompanyProfileMapper.class})
public interface JobMapper extends BaseMapper<JobDto, Job> {

}