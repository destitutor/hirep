package net.hexabrain.hireo.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Company {
    @Id @GeneratedValue
    @Column(name = "company_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private String description;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(mappedBy = "company")
    private final List<Review> reviews = new ArrayList<>();

    @Column(nullable = false)
    private boolean isVerified;

    public Company(String name, Address address, boolean isVerified) {
        this.name = name;
        this.address = address;
        this.isVerified = isVerified;
    }

    public double getAvgRating() {
        if (reviews.isEmpty()) {
            return 0;
        }
        return reviews.stream()
                .mapToDouble(Review::getRating)
                .average().getAsDouble();
    }
}
