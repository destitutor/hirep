package net.hexabrain.hireo.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity @Getter
@DiscriminatorValue("FRE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Freelancer extends Account {
    public Freelancer(String email, String password, AccountType type) {
        super(email, password, type);
    }

    public Freelancer(String email, String password, AccountType type, Profile profile) {
        super(email, password, type, profile);
    }
}