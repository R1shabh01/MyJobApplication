package com.Dummy.MyJobBackend.Service;

import com.Dummy.MyJobBackend.Dto.CompanyDto;
import com.Dummy.MyJobBackend.Entity.Company;
import com.Dummy.MyJobBackend.Entity.Job;
import com.Dummy.MyJobBackend.Repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    public Company createOrUpdate(CompanyDto dto) {
        Company company ;

        if(dto.getId()!=null && companyRepository.existsById(dto.getId())){
            company = companyRepository.findById(dto.getId()).orElseThrow(()-> new RuntimeException("Company not found for an update "+dto.getId()));
            company.setName(dto.getName());
            company.setDescription(dto.getDescription());
            company.setJobs(dto.getJobs());
        }else{
            company = new Company();
            company.setName(dto.getName());
            company.setDescription(dto.getDescription());
            company.setJobs(dto.getJobs());
        }
        return companyRepository.save(company);
    }

    public Company fetchJobById(Long id) {
        Company company = companyRepository.findById(id).orElse(null);
        return company;
    }

    public List<Company> fetchAllJobs() {
        return companyRepository != null ? companyRepository.findAll() : Collections.emptyList();
    }

    public Company deleteById(Long id) {
        Company company = companyRepository.findById(id).orElse(null);
        return company;
    }
}
