package com.pre.wanted.apply.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pre.wanted.apply.dto.AddApplyRequest;
import com.pre.wanted.apply.entity.Apply;
import com.pre.wanted.apply.repository.ApplyRepository;
import com.pre.wanted.company.entity.Company;
import com.pre.wanted.company.repository.CompanyRepository;
import com.pre.wanted.jobNotice.dto.AddJobNoticeRequest;
import com.pre.wanted.jobNotice.dto.JobNoticeResponse;
import com.pre.wanted.jobNotice.entity.JobNotice;
import com.pre.wanted.jobNotice.repository.JobNoticeRepository;
import com.pre.wanted.user.entity.User;
import com.pre.wanted.user.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ApplyControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    ApplyRepository applyRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    JobNoticeRepository jobNoticeRepository;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    public void mockMvcSetUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
    }

    @AfterEach
    public void cleanUp() {
        applyRepository.deleteAll();
        userRepository.deleteAll();
        jobNoticeRepository.deleteAll();
        companyRepository.deleteAll();
    }

    @DisplayName("apply: 사용자가 채용공고 지원에 성공한다.")
    @Test
    public void apply() throws Exception {
        // given
        final String url = "/api/apply";
        Company company = companyRepository.save(Company.builder()
                .name("원티드랩")
                .nation("한국")
                .location("서울")
                .build());
        final Long companyId = company.getId();
        final String position = "백엔드 주니어 개발자";
        final int reward = 1000000;
        final String contents = "원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..";
        final String skill = "Python";
        JobNotice jobNotice = jobNoticeRepository.save(JobNotice.builder()
                .jobNoticeRequest(new AddJobNoticeRequest(companyId, position, reward, contents, skill))
                .company(company)
                .build());
        User user = userRepository.save(User.builder().name("회원1").build());

        final AddApplyRequest applyRequest = new AddApplyRequest(jobNotice.getId(), user.getId());

        final String requestBody = objectMapper.writeValueAsString(applyRequest);

        // when
        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        // then
        result.andExpect(status().isCreated());

        List<Apply> applys = applyRepository.findAll();

        assertThat(applys.size()).isEqualTo(1);
        assertThat(applys.get(0).getJobNotice().getId()).isEqualTo(jobNotice.getId());
        assertThat(applys.get(0).getUser().getId()).isEqualTo(user.getId());
    }
}