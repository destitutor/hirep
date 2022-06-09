package net.hexabrain.hireo.web.job.dto.mapper;

import net.hexabrain.hireo.web.common.mapper.BaseMapper;
import net.hexabrain.hireo.web.job.domain.Job;
import net.hexabrain.hireo.web.job.dto.JobDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface JobMapper extends BaseMapper<JobDto, Job> {
}