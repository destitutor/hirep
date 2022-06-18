package net.hexabrain.hireo.web.job.dto;

import net.hexabrain.hireo.web.job.domain.Category;
import net.hexabrain.hireo.web.job.domain.JobType;

import lombok.Data;

@Data
public class SearchRequest {
    private String keyword;

    private Category category;

    private JobType jobType;

    private String salary;

    private double lng;

    private double lat;

    private double radius;

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
}
