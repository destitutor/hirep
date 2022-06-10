package net.hexabrain.hireo.web.account.domain;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.hexabrain.hireo.web.company.domain.Profile;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity @Getter
@DiscriminatorValue("EMP")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Employer extends Account {
    @Builder
    public Employer(String email, String password, AccountType type, Profile profile) {
        super(email, password, type, profile);
    }
}
