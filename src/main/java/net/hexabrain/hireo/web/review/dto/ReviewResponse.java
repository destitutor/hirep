package net.hexabrain.hireo.web.review.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ReviewResponse {
	private String title;

	private String content;

	private int rating;

	private LocalDateTime postedAt;
}
