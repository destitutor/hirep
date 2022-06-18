package net.hexabrain.hireo.web.job.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JobPostRequestDto {
	@Length(min = 1, max = 32)
	private String name;

	@NotEmpty
	private String type;

	@NotEmpty
	private String category;

	@Min(1)
	private int min;

	@Min(1)
	private int max;

	private Long companyId;
}
