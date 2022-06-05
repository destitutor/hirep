package net.hexabrain.hireo.web.job.repository;

import net.hexabrain.hireo.web.job.domain.Job;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface JobRepository extends PagingAndSortingRepository<Job, Long>, QuerydslPredicateExecutor<Job> {
}
