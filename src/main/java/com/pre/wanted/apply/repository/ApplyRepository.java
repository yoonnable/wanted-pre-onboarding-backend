package com.pre.wanted.apply.repository;

import com.pre.wanted.apply.entity.Apply;
import com.pre.wanted.jobNotice.entity.JobNotice;
import com.pre.wanted.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApplyRepository extends JpaRepository<Apply, Long> {

    Optional<Apply> findByJobNoticeIdAndUserId(long jobNoticeId, long userId);
}
