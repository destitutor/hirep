package net.hexabrain.hireo.web.job.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.hexabrain.hireo.web.company.dto.CompanyDto;
import net.hexabrain.hireo.web.job.domain.Category;
import net.hexabrain.hireo.web.job.domain.JobType;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class JobDto {
    private Long id;

    private String name;

    private JobType jobType;

    private int startSalary;

    private int endSalary;

    private LocalDateTime postedAt;

    private Category category;

    private CompanyDto company;
}
