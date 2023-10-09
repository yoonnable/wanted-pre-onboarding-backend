package com.pre.wanted.company.entity;

import com.pre.wanted.jobNotice.entity.JobNotice;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String nation;

    @Column(nullable = false)
    private String location;

    @OneToMany(mappedBy = "company")
    List<JobNotice> jobNotices;

    @Builder
    public Company(String name, String nation, String location, List<JobNotice> jobNotices) {
        this.name = name;
        this.nation = nation;
        this.location = location;
        this.jobNotices = jobNotices;
    }

    public void update(List<JobNotice> jobNotices) {
        this.jobNotices = jobNotices;
    }
}
