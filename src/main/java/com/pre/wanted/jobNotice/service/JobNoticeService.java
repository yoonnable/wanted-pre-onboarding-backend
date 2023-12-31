package com.pre.wanted.jobNotice.service;

import com.pre.wanted.company.entity.Company;
import com.pre.wanted.company.repository.CompanyRepository;
import com.pre.wanted.company.service.CompanyService;
import com.pre.wanted.jobNotice.dto.AddJobNoticeRequest;
import com.pre.wanted.jobNotice.dto.UpdateJobNoticeRequest;
import com.pre.wanted.jobNotice.entity.JobNotice;
import com.pre.wanted.jobNotice.repository.JobNoticeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class JobNoticeService {

    private final CompanyService companyService;
    private final JobNoticeRepository jobNoticeRepository;

    // 채용공고 등록
    @Transactional
    public JobNotice save(AddJobNoticeRequest request) {
        Company company = companyService.findCompanyById(request.getCompanyId());
        return jobNoticeRepository.save(new JobNotice(request, company));
    }

    // 채용공고 수정
    @Transactional
    public JobNotice update(long id, UpdateJobNoticeRequest request) {
        JobNotice jobNotice = findById(id);

        jobNotice.update(request.getPosition(), request.getReward(), request.getContents(), request.getSkill());

        return jobNotice;
    }

    // 채용공고 삭제
    public void delete(long id) {
        jobNoticeRepository.deleteById(id);
    }

    // 채용공고 목록 조회
    public List<JobNotice> finalAll() {
        return jobNoticeRepository.findAll();
    }

    // 채용공고 검색
    public List<JobNotice> findBySearch(String search) {
        return jobNoticeRepository.findBySearch(search);
    }

    // 채용공고 상세 페이지 조회
    public JobNotice findById(long id) {
        return jobNoticeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found JobNotice: " + id));
    }
}
