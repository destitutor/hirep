package net.hexabrain.hireo.transport.mapper;

import net.hexabrain.hireo.domain.Job;
import net.hexabrain.hireo.transport.dto.JobDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface JobMapper extends BaseMapper<JobDto, Job> {
}