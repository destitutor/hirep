package net.hexabrain.hireo.web.account.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.hexabrain.hireo.web.company.domain.Profile;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity @Getter
@DiscriminatorValue("FRE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Freelancer extends Account {
    @Builder
    public Freelancer(String email, String password, AccountType type, Profile profile) {
        super(email, password, type, profile);
    }
}