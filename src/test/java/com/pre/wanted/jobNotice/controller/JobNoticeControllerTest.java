package com.pre.wanted.jobNotice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pre.wanted.apply.repository.ApplyRepository;
import com.pre.wanted.company.entity.Company;
import com.pre.wanted.company.repository.CompanyRepository;
import com.pre.wanted.jobNotice.dto.AddJobNoticeRequest;
import com.pre.wanted.jobNotice.dto.JobNoticeResponse;
import com.pre.wanted.jobNotice.dto.UpdateJobNoticeRequest;
import com.pre.wanted.jobNotice.entity.JobNotice;
import com.pre.wanted.jobNotice.repository.JobNoticeRepository;
import com.pre.wanted.user.repository.UserRepository;
import jakarta.transaction.Status;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
    }

    @AfterEach
    public void cleanUp() {
        applyRepository.deleteAll();
        jobNoticeRepository.deleteAll();
        companyRepository.deleteAll();
    }

    @DisplayName("addJobNotice: 채용공고 등록에 성공한다.")
    @Test
    public void addJobNotice() throws Exception {
        // given
        final String url = "/api/jobNotice";
        Company company = companyRepository.save(Company.builder()
                .name("원티드랩")
                .nation("한국")
                .location("서울")
                .build());
        final String position = "백엔드 주니어 개발자";
        final int reward = 1000000;
        final String contents = "원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..";
        final String skill = "Python";
        final AddJobNoticeRequest jobNoticeRequest = new AddJobNoticeRequest(company.getId(), position, reward, contents, skill);

        final String requestBody = objectMapper.writeValueAsString(jobNoticeRequest);

        // when: 설정한 내용을 바탕으로 요청 전송
        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        // then
        result.andExpect(status().isCreated());

        List<JobNotice> jobNotices = jobNoticeRepository.findAll();

        assertThat(jobNotices.size()).isEqualTo(1);
        assertThat(jobNotices.get(0).getCompany().getId()).isEqualTo(company.getId());
        assertThat(jobNotices.get(0).getPosition()).isEqualTo(position);
        assertThat(jobNotices.get(0).getReward()).isEqualTo(reward);
        assertThat(jobNotices.get(0).getContents()).isEqualTo(contents);
        assertThat(jobNotices.get(0).getSkill()).isEqualTo(skill);
    }

    @DisplayName("updateJobNotice: 채용공고 수정에 성공한다.")
    @Test
    public void updateJobNotice() throws Exception {
        // given
        final String url = "/api/jobNotice/{id}";
        Company company = companyRepository.save(Company.builder()
                .name("원티드랩")
                .nation("한국")
                .location("서울")
                .build());
        JobNotice jobNotice = save(company);

        final String position = "백엔드 주니어 개발자";
        final int reward = 1500000;
        final String contents = "원티드랩에서 백엔드 주니어 개발자를 '적극' 채용합니다. 자격요건은..";
        final String skill = "Django";

        UpdateJobNoticeRequest request = new UpdateJobNoticeRequest(position, reward, contents, skill);

        // when
        ResultActions result = mockMvc.perform(put(url, jobNotice.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)));

        // then
        result.andExpect(status().isOk());

        JobNotice updatedJobNotice = jobNoticeRepository.findById(jobNotice.getId()).get();

        assertThat(updatedJobNotice.getReward()).isEqualTo(reward);
        assertThat(updatedJobNotice.getContents()).isEqualTo(contents);
        assertThat(updatedJobNotice.getSkill()).isEqualTo(skill);
    }

    @DisplayName("deleteJobNotice: 채용공고 삭제에 성공한다.")
    @Test
    public void deleteJobNotice() throws Exception {
        // given
        final String url = "/api/jobNotice/{id}";
        Company company = companyRepository.save(Company.builder()
                .name("원티드랩")
                .nation("한국")
                .location("서울")
                .build());
        JobNotice jobNotice = save(company);

        // when
        mockMvc.perform(delete(url, jobNotice.getId()))
                .andExpect(status().isOk());

        // then
        List<JobNotice> jobNotices = jobNoticeRepository.findAll();

        assertThat(jobNotices).isEmpty();
    }

    @DisplayName("findAllJobNotice: 채용공고 목록 조회에 성공한다.")
    @Test
    public void findAllJobNotice() throws Exception {
        // given
        final String url = "/api/jobNotice";
        Company company = companyRepository.save(Company.builder()
                .name("원티드랩")
                .nation("한국")
                .location("서울")
                .build());
        JobNotice jobNotice = save(company);

        // when
        final ResultActions resultActions = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].companyName").value("원티드랩"))
                .andExpect(jsonPath("$[0].nation").value("한국"))
                .andExpect(jsonPath("$[0].location").value("서울"))
                .andExpect(jsonPath("$[0].position").value(jobNotice.getPosition()))
                .andExpect(jsonPath("$[0].reward").value(jobNotice.getReward()))
                .andExpect(jsonPath("$[0].skill").value(jobNotice.getSkill()));
    }

    @DisplayName("searchJobNotice: 채용공고 검색에 성공한다.")
    @Test
    public void searchJobNotice() throws Exception {
        // given
        final String url = "/api/jobNotice/search";
        Company company = companyRepository.save(Company.builder()
                .name("원티드랩")
                .nation("한국")
                .location("서울")
                .build());
        save(company);

        // when
        final ResultActions resultActions = mockMvc.perform(get(url).param("search", "원티드")
                .accept(MediaType.APPLICATION_JSON));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].companyName").value(containsString("원티드")));
    }

    @DisplayName("findJobNotice: 채용공고 상세 정보 조회에 성공한다.")
    @Test
    @Transactional
    public void findJobNotice() throws Exception {
        // given
        final String url = "/api/jobNotice/{id}";
        Company company = companyRepository.save(Company.builder()
                .name("원티드랩")
                .nation("한국")
                .location("서울")
                .build());
        JobNotice jobNotice1 = save(company);
        JobNotice jobNotice2 = save(company);
        JobNotice jobNotice3 = save(company);

        List<JobNotice> otherJobNotices = new ArrayList<>();
        otherJobNotices.add(jobNotice1);
        otherJobNotices.add(jobNotice2);
        otherJobNotices.add(jobNotice3);

        company.update(otherJobNotices);
        JobNotice jobNotice = save(company);

        List<Long> otherJobNoticeIds = new ArrayList<>();
        otherJobNoticeIds.add(jobNotice1.getId());
        otherJobNoticeIds.add(jobNotice2.getId());
        otherJobNoticeIds.add(jobNotice3.getId());

        // when
        final ResultActions resultActions = mockMvc.perform(get(url, jobNotice.getId()));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(jobNotice.getId()))
                .andExpect(jsonPath("$.companyName").value("원티드랩"))
                .andExpect(jsonPath("$.nation").value("한국"))
                .andExpect(jsonPath("$.location").value("서울"))
                .andExpect(jsonPath("$.position").value(jobNotice.getPosition()))
                .andExpect(jsonPath("$.reward").value(jobNotice.getReward()))
                .andExpect(jsonPath("$.skill").value(jobNotice.getSkill()))
                .andExpect(jsonPath("$.contents").value(jobNotice.getContents()))
                .andExpect(jsonPath("$.otherJobNotices[0]").value(otherJobNoticeIds.get(0)))
                .andExpect(jsonPath("$.otherJobNotices[1]").value(otherJobNoticeIds.get(1)))
                .andExpect(jsonPath("$.otherJobNotices[2]").value(otherJobNoticeIds.get(2)));
    }

    public JobNotice save(Company company) {

        final Long companyId = company.getId();
        final String position = "백엔드 주니어 개발자";
        final int reward = 1000000;
        final String contents = "원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..";
        final String skill = "Python";

        return jobNoticeRepository.save(JobNotice.builder()
                .jobNoticeRequest(new AddJobNoticeRequest(companyId, position, reward, contents, skill))
                .company(company)
                .build());
    }
}