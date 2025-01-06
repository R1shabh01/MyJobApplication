package com.Dummy.MyJobBackend.Service;


import com.Dummy.MyJobBackend.Dto.JobDto;
import com.Dummy.MyJobBackend.Entity.Company;
import com.Dummy.MyJobBackend.Entity.Job;
import com.Dummy.MyJobBackend.Repository.CompanyRepository;
import com.Dummy.MyJobBackend.Repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class JobService {
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private CompanyRepository companyRepository;
    public List<JobDto> fetchAllJobs() {
    //    return Optional.of(jobRepository.findAll()).orElse(new ArrayList<>()); this is different from below query
            List<Job> jobs =  jobRepository != null ? jobRepository.findAll() : Collections.emptyList();
            List<JobDto> dtos = new ArrayList<>();
            if(jobs!=null){
                for(Job job : jobs){
                    JobDto dto = new JobDto();
                    dto.setId(job.getId());
                    dto.setDescription(job.getDescription());
                    dto.setLocation(job.getLocation());
                    dto.setTitle(job.getTitle());
                    dto.setMinSalary(job.getMinSalary());
                    dto.setMaxSalary(job.getMaxSalary());
                    dto.setCompanyid(job.getCompany()!=null && job.getCompany().getId()!=null ? job.getCompany().getId() : -1L);
                    dtos.add(dto);
                }
                return dtos;
            }else {
                return dtos;
            }
    }

    public Job createOrUpdate(JobDto jobDto) {
        Company company = companyRepository.findById(jobDto.getCompanyid()).orElseThrow(()-> new RuntimeException("Company not found by this id "+jobDto.getCompanyid()));
        Job job ;
        if(jobDto.getId()!=null && jobRepository.existsById(jobDto.getId())){
            job = jobRepository.findById(jobDto.getId()).orElseThrow(()->new RuntimeException("job not found for update:" + jobDto.getId()));
            job.setTitle(jobDto.getTitle());
            job.setDescription(jobDto.getDescription());
            job.setLocation(jobDto.getLocation());
            job.setMinSalary(jobDto.getMinSalary());
            job.setMaxSalary(jobDto.getMaxSalary());
            job.setCompany(company);
        }else {
            job = new Job();
            job.setTitle(jobDto.getTitle());
            job.setDescription(jobDto.getDescription());
            job.setLocation(jobDto.getLocation());
            job.setMinSalary(jobDto.getMinSalary());
            job.setMaxSalary(jobDto.getMaxSalary());
            job.setCompany(company);
        }
        return jobRepository.save(job);
    }

    public Job deleteById(Long id) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found with id: " + id));
        jobRepository.delete(job);
        return job;
    }

    public JobDto fetchById(Long id) {
        Job job = jobRepository.findById(id).orElse(null);
        JobDto dto = new JobDto();
        dto.setId(job.getId());
        dto.setDescription(job.getDescription());
        dto.setLocation(job.getLocation());
        dto.setTitle(job.getTitle());
        dto.setMinSalary(job.getMinSalary());
        dto.setMaxSalary(job.getMaxSalary());
        dto.setCompanyid(job.getCompany().getId());
        return dto;
    }
}
//