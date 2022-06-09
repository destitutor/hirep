package net.hexabrain.hireo.web.company.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {
    @Id @GeneratedValue
    @Column(name = "address_id")
    private Long id;

    @Embedded
    private Coordinate coordinate;

    @Column(nullable = false)
    private String countryCode;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    public Address(Coordinate coordinate, String countryCode) {
        this.coordinate = coordinate;
        this.countryCode = countryCode;
    }
}
