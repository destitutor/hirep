package net.hexabrain.hireo.transport.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
public class SearchResult {
    private List<JobDto> jobs;

    private int page;

    private int totalPages;

    private long totalResults;

    private int pageSize;
}
