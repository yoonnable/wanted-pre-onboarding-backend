package com.pre.wanted.jobNotice.repository;

import com.pre.wanted.jobNotice.entity.JobNotice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobNoticeRepository extends JpaRepository<JobNotice, Long> {
}
