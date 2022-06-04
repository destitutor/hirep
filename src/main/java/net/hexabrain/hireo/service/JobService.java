package net.hexabrain.hireo.service;

import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import net.hexabrain.hireo.domain.Job;
import net.hexabrain.hireo.domain.QJob;
import net.hexabrain.hireo.repository.JobRepository;
import net.hexabrain.hireo.transport.dto.SearchRequest;
import net.hexabrain.hireo.transport.dto.SearchResult;
import net.hexabrain.hireo.transport.mapper.JobMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class JobService {
    private final JobRepository jobRepository;
    private final JobMapper jobMapper;

    public SearchResult search(SearchRequest searchRequest, int page) {
        QJob qJob = QJob.job;
        BooleanBuilder builder = new BooleanBuilder();

        if (searchRequest.getKeyword() != null) {
            builder.and(qJob.name.contains(searchRequest.getKeyword()));
        }
        if (searchRequest.getCategory() != null) {
            builder.and(qJob.category.eq(searchRequest.getCategory()));
        }
        if (searchRequest.getJobType() != null) {
            builder.and(qJob.jobType.eq(searchRequest.getJobType()));
        }
        if (searchRequest.getStartSalary() != 0) {
            builder.and(qJob.startSalary.goe(searchRequest.getStartSalary()));
        }
        if (searchRequest.getEndSalary() != 0) {
            builder.and(qJob.endSalary.loe(searchRequest.getEndSalary()));
        }

        PageRequest pageRequest = PageRequest.of(page - 1, 10);
        Page<Job> jobs = jobRepository.findAll(builder, pageRequest);
        return SearchResult.builder()
                .jobs(jobs.getContent().stream()
                        .map(jobMapper::toDto)
                        .collect(toList()))
                .pageSize(10)
                .totalPages(jobs.getTotalPages())
                .totalResults(jobs.getTotalElements())
                .page(page)
                .build();
    }

    public Job findOne(Long id) {
        return jobRepository.findById(id).get();
    }

    public Iterable<Job> findAll() {
        return jobRepository.findAll();
    }
}
