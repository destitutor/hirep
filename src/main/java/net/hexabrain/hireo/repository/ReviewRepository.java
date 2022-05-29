package net.hexabrain.hireo.repository;

import net.hexabrain.hireo.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}