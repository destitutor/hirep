package net.hexabrain.hireo.transport.dto;

import lombok.Data;
import net.hexabrain.hireo.domain.Address;
import net.hexabrain.hireo.domain.Review;

import java.util.List;

@Data
public class CompanyDto {
    private String name;

    private String description;

    private Address address;

    private List<Review> reviews;

    private boolean isVerified;
}
