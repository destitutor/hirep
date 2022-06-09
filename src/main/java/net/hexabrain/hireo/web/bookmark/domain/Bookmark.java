package net.hexabrain.hireo.web.bookmark.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.hexabrain.hireo.web.account.domain.Account;
import net.hexabrain.hireo.web.company.domain.Company;
import net.hexabrain.hireo.web.job.domain.Job;

import javax.persistence.*;

@Entity
@Getter
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"account_id", "company_id"}),
        @UniqueConstraint(columnNames = {"account_id", "job_id"})})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bookmark {
    @Id @GeneratedValue
    @Column(name = "bookmark_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id")
    private Job job;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    public Bookmark(Account account, Company company) {
        this.account = account;
        this.company = company;
    }

    public Bookmark(Account account, Job job) {
        this.account = account;
        this.job = job;
    }
}
