package com.pre.wanted.jobNotice.dto;

import com.pre.wanted.jobNotice.entity.JobNotice;
import lombok.Getter;
import org.hibernate.collection.spi.PersistentBag;

import java.util.ArrayList;
import java.util.List;
@Getter
public class JobNoticeDetailResponse {

    private final Long id;
    private final String companyName;
    private final String nation;
    private final String location;
    private final String position;
    private final int reward;
    private final String skill;
    private final String contents;
    private final List<Long> otherJobNotices;

    public JobNoticeDetailResponse(JobNotice jobNotice) {
        this.id = jobNotice.getId();
        this.companyName = jobNotice.getCompany().getName();
        this.nation = jobNotice.getCompany().getNation();
        this.location = jobNotice.getCompany().getLocation();
        this.position = jobNotice.getPosition();
        this.reward = jobNotice.getReward();
        this.skill = jobNotice.getSkill();
        this.contents = jobNotice.getContents();
        this.otherJobNotices = jobNotice.getCompany().getJobNotices()
                .stream()
                .filter(otherJobNotice -> !otherJobNotice.getId().equals(this.id))
                .map(JobNotice::getId).toList();
    }
}
