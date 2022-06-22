package net.hexabrain.hireo.web.job.service;

import static com.querydsl.core.types.dsl.MathExpressions.*;
import static java.util.stream.Collectors.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
import net.hexabrain.hireo.web.job.dto.JobDetailsResponse;
import net.hexabrain.hireo.web.job.dto.JobPostRequest;
import net.hexabrain.hireo.web.job.dto.JobSearchRequest;
import net.hexabrain.hireo.web.job.dto.JobSearchResponse;
import net.hexabrain.hireo.web.job.dto.SortType;
import net.hexabrain.hireo.web.job.dto.mapper.JobDetailsMapper;
import net.hexabrain.hireo.web.job.dto.mapper.JobInfoMapper;
import net.hexabrain.hireo.web.job.dto.mapper.JobPostRequestMapper;
import net.hexabrain.hireo.web.job.repository.JobRepository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
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
    private final JobInfoMapper jobInfoMapper;
    private final JobDetailsMapper jobDetailsMapper;
    private final JobPostRequestMapper jobPostRequestMapper;

    public Long post(User poster, JobPostRequest dto) {
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
    public JobSearchResponse search(JobSearchRequest searchRequest) {
        QJob qJob = QJob.job;
        BooleanBuilder builder = new BooleanBuilder();

        if (searchRequest.getKeyword() != null) {
            builder.and(qJob.name.contains(searchRequest.getKeyword()));
        }
        if (searchRequest.getCategories() != null) {
            builder.and(qJob.category.in(searchRequest.getCategoriesAsEnum()));
        }
        if (searchRequest.getJobType() != null) {
            builder.and(qJob.jobType.in(searchRequest.getJobTypesAsEnum()));
        }
        if (searchRequest.getStartSalary() != 0) {
            builder.and(qJob.startSalary.goe(searchRequest.getStartSalary()));
        }
        if (searchRequest.getEndSalary() != 0) {
            builder.and(qJob.endSalary.loe(searchRequest.getEndSalary()));
        }
        if (searchRequest.getLat() != 0 && searchRequest.getLng() != 0) {
            builder.and(isWithinRange(qJob, searchRequest.getLat(), searchRequest.getLng(), searchRequest.getRadius()));
        }

        PageRequest pageRequest = PageRequest.of(searchRequest.getPage() - 1, 10, getSortingCriteria(searchRequest.getSort()));
        Page<Job> jobs = jobRepository.findAll(builder, pageRequest);
        return JobSearchResponse.builder()
                .jobs(jobs.getContent().stream()
                        .map(jobInfoMapper::toDto)
                        .collect(toList()))
                .pageSize(pageRequest.getPageSize())
                .totalPages(jobs.getTotalPages())
                .totalElements(jobs.getTotalElements())
                .page(searchRequest.getPage())
                .build();
    }

    private Sort getSortingCriteria(SortType sortType) {
        if (sortType.equals(SortType.RECENT)) {
            return Sort.by(Sort.Direction.DESC, "createdDate");
        }
        if (sortType.equals(SortType.HIGHEST_SALARY)) {
            return Sort.by(Sort.Direction.DESC, "startSalary");
        }
        if (sortType.equals(SortType.LOWEST_SALARY)) {
            return Sort.by(Sort.Direction.ASC, "startSalary");
        }
        throw new IllegalArgumentException("알 수 없는 정렬 기준: " + sortType);
    }

    private Predicate isWithinRange(QJob alias, double latitude, double longitude, double radius) {
        NumberPath<Double> lat = alias.company.address.coordinate.latitude;
        NumberPath<Double> lng = alias.company.address.coordinate.longitude;
        NumberExpression<Double> formula = (acos(cos(radians(Expressions.constant(latitude)))
            .multiply(cos(radians(lat))
                .multiply(cos(radians(lng).subtract(radians(Expressions.constant(longitude)))
                    .add(sin(radians(Expressions.constant(latitude)))
                        .multiply(sin(radians(lat))))))))
            .multiply(Expressions.constant(6371)));
        return formula.loe(radius);
    }

    @Transactional(readOnly = true)
    public long count() {
        return jobRepository.count();
    }

    @Transactional(readOnly = true)
    public JobDetailsResponse findOne(Long id) {
        Job foundJob = jobRepository.findByIdOrThrow(id);
        return jobDetailsMapper.toDto(foundJob);
    }

    @Transactional(readOnly = true)
    public Iterable<Job> findAll() {
        return jobRepository.findAll();
    }
}
