package net.hexabrain.hireo.web.company.dto;

import java.util.List;

import net.hexabrain.hireo.web.review.dto.ReviewResponse;

import lombok.Builder;
import lombok.Data;

@Data
public class CompanyProfileResponse {
    private Long id;

    private String name;

    private String description;

    private AddressResponse address;

    private List<ReviewResponse> reviews;

    private double averageRating;

    private boolean isVerified;

    @Builder
    public CompanyProfileResponse(Long id, String name, String description, AddressResponse address,
        List<ReviewResponse> reviews, boolean isVerified) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.address = address;
        this.reviews = reviews;
        this.isVerified = isVerified;

        if (reviews != null) {
            averageRating = reviews.stream()
                .mapToDouble(ReviewResponse::getRating)
                .average()
                .orElse(0);
        }
    }
}
