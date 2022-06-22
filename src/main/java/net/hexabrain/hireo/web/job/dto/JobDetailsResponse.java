package net.hexabrain.hireo.web.job.dto;

import java.time.LocalDateTime;

import net.hexabrain.hireo.web.company.dto.CompanyDetailsResponse;
import net.hexabrain.hireo.web.job.domain.Category;
import net.hexabrain.hireo.web.job.domain.JobType;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JobDetailsResponse {
    private Long id;

    private String name;

    private String description;

    private JobType jobType;

    private int startSalary;

    private int endSalary;

    private LocalDateTime postedAt;

    private Category category;

    private CompanyDetailsResponse company;
}
