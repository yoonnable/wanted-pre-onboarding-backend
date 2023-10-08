package com.pre.wanted.apply.entity;

import com.pre.wanted.jobNotice.entity.JobNotice;
import com.pre.wanted.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Apply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "job_notice_id")
    private JobNotice jobNotice;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Apply(JobNotice jobNotice, User user) {
        this.jobNotice = jobNotice;
        this.user = user;
    }
}
