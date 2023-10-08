package com.pre.wanted.jobNotice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateJobNoticeRequest {
    private String position;
    private int reward;
    private String contents;
    private String skill;
}
