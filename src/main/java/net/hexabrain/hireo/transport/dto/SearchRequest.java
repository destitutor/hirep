package net.hexabrain.hireo.transport.dto;

import lombok.Data;
import net.hexabrain.hireo.domain.Category;
import net.hexabrain.hireo.domain.JobType;

import java.util.List;

@Data
public class SearchRequest {
    private String keyword;

    private Category category;

    private JobType jobType;

    private String salary;

    private List<String> tags;

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
