package com.pre.wanted.jobNotice.entity;

import com.pre.wanted.company.entity.Company;
import com.pre.wanted.jobNotice.dto.AddJobNoticeRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JobNotice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id")
    private Company company;

    @Column(nullable = false)
    private String position;

    @Column(nullable = false)
    private int reward;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private String skill;

    @Builder
    public JobNotice(AddJobNoticeRequest jobNoticeRequest, Company company) {
        this.company = company;
        this.position = jobNoticeRequest.getPosition();
        this.reward = jobNoticeRequest.getReward();
        this.contents = jobNoticeRequest.getContents();
        this.skill = jobNoticeRequest.getSkill();
    }

}
