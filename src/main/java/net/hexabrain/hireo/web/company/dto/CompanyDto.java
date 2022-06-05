package net.hexabrain.hireo.web.company.dto;

import lombok.Data;
import net.hexabrain.hireo.web.company.domain.Address;
import net.hexabrain.hireo.web.review.domain.Review;

import java.util.List;

@Data
public class CompanyDto {
    private String name;

    private String description;

    private Address address;

    private List<Review> reviews;

    private boolean isVerified;
}
