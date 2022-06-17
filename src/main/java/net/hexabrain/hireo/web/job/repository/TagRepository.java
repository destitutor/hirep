package net.hexabrain.hireo.web.job.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.hexabrain.hireo.web.job.domain.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
