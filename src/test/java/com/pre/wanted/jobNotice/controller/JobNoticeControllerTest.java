package com.pre.wanted.jobNotice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pre.wanted.apply.repository.ApplyRepository;
import com.pre.wanted.company.entity.Company;
import com.pre.wanted.company.repository.CompanyRepository;
import com.pre.wanted.jobNotice.dto.AddJobNoticeRequest;
import com.pre.wanted.jobNotice.entity.JobNotice;
import com.pre.wanted.jobNotice.repository.JobNoticeRepository;
import com.pre.wanted.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class JobNoticeControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    JobNoticeRepository jobNoticeRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ApplyRepository applyRepository;

    @BeforeEach
    public void mockMvcSetUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        applyRepository.deleteAll();
        userRepository.deleteAll();
        jobNoticeRepository.deleteAll();
        companyRepository.deleteAll();
    }

    @DisplayName("addJobNotice: 채용공고 등록에 성공한다.")
    @Test
    public void addJobNotice() throws Exception {
        // given
        Company company = companyRepository.save(new Company("원티드랩", "한국", "서울", null));

        final String url = "/api/jobNotice";
        final Long companyId = company.getId();
        final String position = "백엔드 주니어 개발자";
        final int reward = 1000000;
        final String contents = "원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..";
        final String skill = "Python";
        final AddJobNoticeRequest jobNoticeRequest = new AddJobNoticeRequest(companyId, position, reward, contents, skill);

        final String requestBody = objectMapper.writeValueAsString(jobNoticeRequest);

        // when: 설정한 내용을 바탕으로 요청 전송
        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        // then
        result.andExpect(status().isCreated());

        List<JobNotice> jobNotices = jobNoticeRepository.findAll();

        assertThat(jobNotices.size()).isEqualTo(1);
        assertThat(jobNotices.get(0).getCompany().getId()).isEqualTo(companyId);
        assertThat(jobNotices.get(0).getPosition()).isEqualTo(position);
        assertThat(jobNotices.get(0).getReward()).isEqualTo(reward);
        assertThat(jobNotices.get(0).getContents()).isEqualTo(contents);
        assertThat(jobNotices.get(0).getSkill()).isEqualTo(skill);
    }
}