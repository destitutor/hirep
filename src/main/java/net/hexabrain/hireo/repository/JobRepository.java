package net.hexabrain.hireo.repository;

import net.hexabrain.hireo.domain.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {
}
