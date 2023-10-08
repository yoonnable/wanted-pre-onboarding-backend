package com.pre.wanted.jobNotice.repository;

import com.pre.wanted.jobNotice.dto.JobNoticeResponse;
import com.pre.wanted.jobNotice.entity.JobNotice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JobNoticeRepository extends JpaRepository<JobNotice, Long> {

    @Query(value = """
            SELECT j.*
              FROM job_notice j
              LEFT JOIN company c
                ON j.company_id = c.id
             WHERE c.name LIKE %:search%
                OR c.nation LIKE %:search%
                OR c.location LIKE %:search%
                OR j.position LIKE %:search%
                OR j.reward LIKE %:search%
                OR j.skill LIKE %:search%
            """, nativeQuery = true)
    List<JobNotice> findBySearch(@Param("search") String search);
}
