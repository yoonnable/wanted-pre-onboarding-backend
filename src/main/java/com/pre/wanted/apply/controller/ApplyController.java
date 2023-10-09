package com.pre.wanted.apply.controller;

import com.pre.wanted.apply.dto.AddApplyRequest;
import com.pre.wanted.apply.entity.Apply;
import com.pre.wanted.apply.service.ApplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ApplyController {

    private final ApplyService applyService;

    @PostMapping("/api/apply")
    public ResponseEntity<Apply> apply(@RequestBody AddApplyRequest request) {
        Apply savedApply = applyService.apply(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedApply);
    }
}
