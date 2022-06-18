package net.hexabrain.hireo.web.job.dto.mapper;

import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import net.hexabrain.hireo.web.common.exception.company.NoSuchJobCategoryException;
import net.hexabrain.hireo.web.common.exception.company.NoSuchJobTypeException;
import net.hexabrain.hireo.web.common.mapper.BaseMapper;
import net.hexabrain.hireo.web.job.domain.Category;
import net.hexabrain.hireo.web.job.domain.Job;
import net.hexabrain.hireo.web.job.domain.JobType;
import net.hexabrain.hireo.web.job.dto.JobPostRequestDto;

@Mapper(componentModel = "spring")
public interface JobPostRequestMapper extends BaseMapper<JobPostRequestDto, Job> {

	@Mapping(target = "company", ignore = true)
	@Mapping(source = "min", target = "startSalary")
	@Mapping(source = "max", target = "endSalary")
	@Mapping(source = "type", target = "jobType")
	Job toEntity(JobPostRequestDto dto);

	@BeforeMapping
	default void beforeMapping(JobPostRequestDto dto) {
		dto.setCategory(mapToCategory(dto.getCategory()));
		dto.setType(mapToJobType(dto.getType()));
	}

	private String mapToCategory(String category) {
		Category[] categories = Category.values();
		for (Category element : categories) {
			if (element.name().equals(category) || element.getName().equals(category)) {
				return element.name();
			}
		}
		throw new NoSuchJobCategoryException();
	}

	private String mapToJobType(String jobType) {
		JobType[] jobTypes = JobType.values();
		for (JobType element : jobTypes) {
			if (element.name().equals(jobType) || element.getName().equals(jobType)) {
				return element.name();
			}
		}
		throw new NoSuchJobTypeException();
	}
}