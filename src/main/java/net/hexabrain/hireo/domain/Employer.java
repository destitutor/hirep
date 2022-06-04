package net.hexabrain.hireo.domain;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity @Getter
@DiscriminatorValue("EMP")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Employer extends Account {
    public Employer(String email, String password, AccountType type) {
        super(email, password, type);
    }

    public Employer(String email, String password, AccountType type, Profile profile) {
        super(email, password, type, profile);
    }
}
