package com.pre.wanted.jobNotice.dto;

import com.pre.wanted.jobNotice.entity.JobNotice;
import lombok.Getter;

@Getter
public class JobNoticeResponse {

    private final Long id;
    private final String companyName;
    private final String nation;
    private final String location;
    private final String position;
    private final int reward;
    private final String skill;

    public JobNoticeResponse(JobNotice jobNotice) {
        this.id = jobNotice.getId();
        this.companyName = jobNotice.getCompany().getName();
        this.nation = jobNotice.getCompany().getNation();
        this.location = jobNotice.getCompany().getLocation();
        this.position = jobNotice.getPosition();
        this.reward = jobNotice.getReward();
        this.skill = jobNotice.getSkill();
    }
}
