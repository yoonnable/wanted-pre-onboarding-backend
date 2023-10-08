package com.pre.wanted.company.service;

import com.pre.wanted.company.entity.Company;
import com.pre.wanted.company.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    public Company findCompanyById(long id) {
        return companyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not fount company: " + id));
    }
}
