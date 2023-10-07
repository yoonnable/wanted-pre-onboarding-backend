package com.pre.wanted.jobNotice.dto;

import com.pre.wanted.jobNotice.entity.JobNotice;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddJobNoticeRequest {

    private Long companyId;
    private String position;
    private int reward;
    private String contents;
    private String skill;

}
