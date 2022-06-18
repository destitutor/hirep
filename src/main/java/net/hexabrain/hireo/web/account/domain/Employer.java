package net.hexabrain.hireo.web.account.domain;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import net.hexabrain.hireo.web.company.domain.Company;

@Entity @Getter
@DiscriminatorValue("EMP")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Employer extends Account {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    @Builder
    public Employer(String email, String password, AccountType type, Profile profile) {
        super(email, password, type, profile);
    }
}
