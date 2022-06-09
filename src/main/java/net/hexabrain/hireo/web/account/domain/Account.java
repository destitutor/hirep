package net.hexabrain.hireo.web.account.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.hexabrain.hireo.web.bookmark.domain.Bookmark;
import net.hexabrain.hireo.web.company.domain.Profile;
import net.hexabrain.hireo.web.review.domain.Review;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @ToString
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "role")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Account {
    @Id @GeneratedValue
    @Column(name = "account_id")
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private AccountType type;

    @Embedded
    private Profile profile;

    @ToString.Exclude
    @OneToMany(mappedBy = "author")
    private List<Review> reviews = new ArrayList<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "account")
    private List<Bookmark> bookmarks = new ArrayList<>();

    protected Account(String email, String password, AccountType type) {
        this.email = email;
        this.password = password;
        this.type = type;
    }

    protected Account(String email, String password, AccountType type, Profile profile) {
        this(email, password, type);
        this.profile = profile;
    }

    public void encodePassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }

    public boolean isBookmarked(Long companyId) {
        return bookmarks.stream()
                .anyMatch(bookmark -> bookmark.getCompany().getId().equals(companyId));
    }
}
