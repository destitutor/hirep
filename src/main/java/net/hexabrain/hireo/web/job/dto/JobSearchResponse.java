package net.hexabrain.hireo.web.job.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
public class JobSearchResponse {
    private List<JobInfoResponse> jobs;

    private int page;

    private int totalPages;

    private long totalElements;

    private int pageSize;
}
