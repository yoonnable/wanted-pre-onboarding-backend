package com.pre.wanted.jobNotice.controller;

import com.pre.wanted.jobNotice.dto.AddJobNoticeRequest;
import com.pre.wanted.jobNotice.entity.JobNotice;
import com.pre.wanted.jobNotice.service.JobNoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class JobNoticeController {

    private final JobNoticeService jobNoticeService;

    @PostMapping("/api/jobNotice")
    public ResponseEntity<JobNotice> addJobNotice(@RequestBody AddJobNoticeRequest request) {
        JobNotice savedJobNotice = jobNoticeService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedJobNotice);
    }
}
