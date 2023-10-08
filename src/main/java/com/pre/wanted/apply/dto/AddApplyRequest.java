package com.pre.wanted.apply.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddApplyRequest {

    private Long jobNoticeId;
    private Long userId;
}
