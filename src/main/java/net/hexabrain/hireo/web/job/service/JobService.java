package net.hexabrain.hireo.web.job.service;

import static com.querydsl.core.types.dsl.MathExpressions.*;
import static java.util.stream.Collectors.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.hexabrain.hireo.web.account.domain.Account;
import net.hexabrain.hireo.web.account.domain.AccountType;
import net.hexabrain.hireo.web.account.domain.Employer;
import net.hexabrain.hireo.web.account.repository.AccountRepository;
import net.hexabrain.hireo.web.common.exception.company.UnauthorizedException;
import net.hexabrain.hireo.web.job.domain.Job;
import net.hexabrain.hireo.web.job.domain.QJob;
import net.hexabrain.hireo.web.job.dto.JobDto;
import net.hexabrain.hireo.web.job.dto.JobPostRequestDto;
import net.hexabrain.hireo.web.job.dto.SearchRequest;
import net.hexabrain.hireo.web.job.dto.SearchResult;
import net.hexabrain.hireo.web.job.dto.mapper.JobMapper;
import net.hexabrain.hireo.web.job.dto.mapper.JobPostRequestMapper;
import net.hexabrain.hireo.web.job.repository.JobRepository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.NumberPath;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class JobService {
    private final JobRepository jobRepository;
    private final AccountRepository accountRepository;

    // Mapper
    private final JobMapper jobMapper;
    private final JobPostRequestMapper jobPostRequestMapper;

    public Long post(User poster, JobPostRequestDto dto) {
        Account account = accountRepository.findByEmailOrThrow(poster.getUsername());
        validatePoster(account);

        Job job = jobPostRequestMapper.toEntity(dto);
        job.setCompany(((Employer)account).getCompany());
        job = jobRepository.save(job);
        return job.getId();
    }

    private void validatePoster(Account poster) {
        if (!isEmployer(poster)) {
            throw new UnauthorizedException();
        }
    }

    private boolean isEmployer(Account account) {
        return account.getType().equals(AccountType.EMPLOYER);
    }

    @Transactional(readOnly = true)
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

    @Transactional(readOnly = true)
    public long count() {
        return jobRepository.count();
    }

    @Transactional(readOnly = true)
    public JobDto findOne(Long id) {
        Job foundJob = jobRepository.findByIdOrThrow(id);
        return jobMapper.toDto(foundJob);
    }

    @Transactional(readOnly = true)
    public Iterable<Job> findAll() {
        return jobRepository.findAll();
    }
}
