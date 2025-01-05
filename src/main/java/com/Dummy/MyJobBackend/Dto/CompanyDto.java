package com.Dummy.MyJobBackend.Dto;

import com.Dummy.MyJobBackend.Entity.Job;
import lombok.Data;

import java.util.List;

@Data
public class CompanyDto {
    private Long id;
    private String name;
    private String description;
    private List<Job> jobs;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }
}
