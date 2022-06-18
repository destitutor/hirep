package net.hexabrain.hireo.web.job.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import net.hexabrain.hireo.web.account.domain.BaseTimeEntity;
import net.hexabrain.hireo.web.company.domain.Company;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Job extends BaseTimeEntity {
    @Id @GeneratedValue
    @Column(name = "job_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private JobType jobType;

    @Column(nullable = false)
    private int startSalary;

    @Column(nullable = false)
    private int endSalary;

    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Builder
    public Job(String name, JobType jobType, int startSalary, int endSalary, Category category, Company company) {
        this.name = name;
        this.jobType = jobType;
        this.startSalary = startSalary;
        this.endSalary = endSalary;
        this.category = category;
        this.company = company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
