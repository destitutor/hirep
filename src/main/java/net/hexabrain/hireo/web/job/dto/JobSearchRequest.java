package net.hexabrain.hireo.web.job.dto;

import java.util.List;
import java.util.stream.Collectors;

import net.hexabrain.hireo.web.job.domain.Category;
import net.hexabrain.hireo.web.job.domain.JobType;

import lombok.Data;

@Data
public class JobSearchRequest {
    private String keyword;

    private List<String> categories;

    private List<String> jobType;

    private String salary;

    private double lng;

    private double lat;

    private double radius = 5;

    private int page = 1;

    private SortType sort = SortType.RECENT;

    public int getStartSalary() {
        if (salary == null) {
            return 0;
        }
        String[] splittedData = this.salary.split(",");
        return Integer.parseInt(splittedData[0]);
    }

    public int getEndSalary()
    {
        if (salary == null) {
            return 0;
        }
        String[] splittedData = this.salary.split(",");
        return Integer.parseInt(splittedData[1]);
    }

    public List<Category> getCategoriesAsEnum() {
        return categories.stream()
            .map(Category::from)
            .collect(Collectors.toList());
    }

    public List<JobType> getJobTypesAsEnum() {
        return jobType.stream()
            .map(JobType::from)
            .collect(Collectors.toList());
    }
}
