package net.hexabrain.hireo.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review {
    @Id @GeneratedValue
    @Column(name = "review_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private int rating;

    @Column(name = "posted_at", nullable = false, columnDefinition = "DATE")
    private LocalDate postedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    public Review(String title, String content, int rating, Account author, Company company) {
        this.title = title;
        this.content = content;
        this.rating = rating;
        this.author = author;
        this.company = company;
    }

    public void setAuthor(Account author) {
        this.author = author;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public void setPostedAt(LocalDate postedAt) {
        this.postedAt = postedAt;
    }
}
