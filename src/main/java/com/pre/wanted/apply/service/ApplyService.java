package com.pre.wanted.apply.service;

import com.pre.wanted.apply.dto.AddApplyRequest;
import com.pre.wanted.apply.entity.Apply;
import com.pre.wanted.apply.repository.ApplyRepository;
import com.pre.wanted.jobNotice.entity.JobNotice;
import com.pre.wanted.jobNotice.service.JobNoticeService;
import com.pre.wanted.user.entity.User;
import com.pre.wanted.user.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ApplyService {

    private final JobNoticeService jobNoticeService;
    private final UserService userService;
    private final ApplyRepository applyRepository;

    // 사용자가 채용공고에 지원
    @Transactional
    public Apply apply(AddApplyRequest request) {
        JobNotice jobNotice = jobNoticeService.findById(request.getJobNoticeId());
        User user = userService.findById(request.getUserId());

        checkDuplicates(jobNotice, user);

        return applyRepository.save(new Apply(jobNotice, user));

    }

    public void checkDuplicates(JobNotice jobNotice, User user) {
        if(findByUserIdAndJobNoticeId(jobNotice, user) != null) {
            throw new IllegalArgumentException("[Duplicated] userId: " + user.getId() + ", jobNoticeId: " + jobNotice.getId());
        }
    }

    // 사용자ID AND 채용공고ID로 지원내역 조회
    public Apply findByUserIdAndJobNoticeId(JobNotice jobNotice, User user) {
        return applyRepository.findByJobNoticeIdAndUserId(jobNotice.getId(), user.getId()).orElse(null);
    }
}
