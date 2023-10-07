package com.pre.wanted.jobNotice.service;

import com.pre.wanted.company.entity.Company;
import com.pre.wanted.company.repository.CompanyRepository;
import com.pre.wanted.jobNotice.dto.AddJobNoticeRequest;
import com.pre.wanted.jobNotice.entity.JobNotice;
import com.pre.wanted.jobNotice.repository.JobNoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JobNoticeService {

    private final JobNoticeRepository jobNoticeRepository;
    private final CompanyRepository companyRepository;

    // 채용공고 등록
    public JobNotice save(AddJobNoticeRequest request) {
        Company company = findCompanyById(request.getCompanyId());
        return jobNoticeRepository.save(new JobNotice(request, company));
    }

    public Company findCompanyById(long id) {
        return companyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not fount company: " + id));
    }
}
