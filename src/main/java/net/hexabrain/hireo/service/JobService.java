package net.hexabrain.hireo.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.NumberPath;
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

import static com.querydsl.core.types.dsl.MathExpressions.*;
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
        if (searchRequest.getLat() != 0 && searchRequest.getLng() != 0) {
            NumberPath<Double> lat = qJob.company.address.coordinate.latitude;
            NumberPath<Double> lng = qJob.company.address.coordinate.longitude;
            NumberExpression<Double> formula = (acos(cos(radians(Expressions.constant(searchRequest.getLat())))
                            .multiply(cos(radians(lat))
                                    .multiply(cos(radians(lng).subtract(radians(Expressions.constant(searchRequest.getLng())))
                                            .add(sin(radians(Expressions.constant(searchRequest.getLat())))
                                                    .multiply(sin(radians(lat))))))))
                            .multiply(Expressions.constant(6371)));
            builder.and(formula.loe(searchRequest.getRadius()));
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

    public long count() {
        return jobRepository.count();
    }

    public Job findOne(Long id) {
        return jobRepository.findById(id).get();
    }

    public Iterable<Job> findAll() {
        return jobRepository.findAll();
    }
}
