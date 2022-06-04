package net.hexabrain.hireo.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Job {
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

    @Column(nullable = false)
    private LocalDateTime postedAt;

    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    public Job(String name, JobType jobType, int startSalary, int endSalary, LocalDateTime postedAt, Category category, Company company) {
        this.name = name;
        this.jobType = jobType;
        this.startSalary = startSalary;
        this.endSalary = endSalary;
        this.postedAt = postedAt;
        this.category = category;
        this.company = company;
    }
}
