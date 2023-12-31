package com.pre.wanted.jobNotice.controller;

import com.pre.wanted.jobNotice.dto.AddJobNoticeRequest;
import com.pre.wanted.jobNotice.dto.JobNoticeDetailResponse;
import com.pre.wanted.jobNotice.dto.JobNoticeResponse;
import com.pre.wanted.jobNotice.dto.UpdateJobNoticeRequest;
import com.pre.wanted.jobNotice.entity.JobNotice;
import com.pre.wanted.jobNotice.service.JobNoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class JobNoticeController {

    private final JobNoticeService jobNoticeService;

    @PostMapping("/api/jobNotice")
    public ResponseEntity<JobNotice> addJobNotice(@RequestBody AddJobNoticeRequest request) {
        JobNotice savedJobNotice = jobNoticeService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedJobNotice);
    }

    @PutMapping("/api/jobNotice/{id}")
    public ResponseEntity<JobNotice> updateJobNotice(@PathVariable long id, @RequestBody UpdateJobNoticeRequest request) {
        JobNotice updatedJobNotice = jobNoticeService.update(id, request);

        return ResponseEntity.ok().body(updatedJobNotice);
    }

    @DeleteMapping("/api/jobNotice/{id}")
    public ResponseEntity<JobNotice> deleteJobNotice(@PathVariable long id) {
        jobNoticeService.delete(id);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/jobNotice")
    public ResponseEntity<List<JobNoticeResponse>> findAllJobNotice() {
        List<JobNoticeResponse> jobNoticeResponses = jobNoticeService.finalAll()
                .stream()
                .map(JobNoticeResponse::new)
                .toList();

        return ResponseEntity.ok().body(jobNoticeResponses);
    }

    @GetMapping("/api/jobNotice/search")
    public ResponseEntity<List<JobNoticeResponse>> searchJobNotice(@RequestParam String search) {
        List<JobNoticeResponse> jobNoticeResponses = jobNoticeService.findBySearch(search)
                .stream()
                .map(JobNoticeResponse::new)
                .toList();

        return ResponseEntity.ok().body(jobNoticeResponses);
    }

    @GetMapping("/api/jobNotice/{id}")
    public ResponseEntity<JobNoticeDetailResponse> findJobNotice(@PathVariable long id) {
        JobNotice jobNotice = jobNoticeService.findById(id);

        return ResponseEntity.ok().body(new JobNoticeDetailResponse(jobNotice));
    }
}
