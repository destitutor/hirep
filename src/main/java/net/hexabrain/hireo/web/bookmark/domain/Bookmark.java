package net.hexabrain.hireo.web.bookmark.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import net.hexabrain.hireo.web.account.domain.Account;
import net.hexabrain.hireo.web.account.domain.BaseTimeEntity;
import net.hexabrain.hireo.web.company.domain.Company;
import net.hexabrain.hireo.web.job.domain.Job;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"account_id", "company_id"}),
        @UniqueConstraint(columnNames = {"account_id", "job_id"})})
@NoArgsConstructor
public class Bookmark extends BaseTimeEntity {
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
