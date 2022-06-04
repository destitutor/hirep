package net.hexabrain.hireo.repository;

import net.hexabrain.hireo.domain.Job;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface JobRepository extends PagingAndSortingRepository<Job, Long>, QuerydslPredicateExecutor<Job> {
}
