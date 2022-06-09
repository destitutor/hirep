package net.hexabrain.hireo.web.review.repository;

import net.hexabrain.hireo.web.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}