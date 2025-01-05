package com.Dummy.MyJobBackend.Controller;


import com.Dummy.MyJobBackend.Dto.ApiResponse;
import com.Dummy.MyJobBackend.Dto.JobDto;
import com.Dummy.MyJobBackend.Entity.Job;
import com.Dummy.MyJobBackend.Service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/job",produces = MediaType.APPLICATION_JSON_VALUE)
public class JobController {
    @Autowired
    private JobService jobService;

    @GetMapping("/")
    public ApiResponse testApi() {
        ApiResponse response = new ApiResponse("ok", "success", null);
        return response;
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createOrUpdate(@RequestBody JobDto jobDto) {
        Job job = jobService.createOrUpdate(jobDto);
        try {
            ApiResponse response = new ApiResponse("ok", "successfully created the job post", job);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (RuntimeException e) {
            ApiResponse error = new ApiResponse("error", "job not found for update" + e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (Exception e) {
            ApiResponse error = new ApiResponse("error", "failed to create the job post " + e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @GetMapping("/fetch")
    public ResponseEntity<ApiResponse> fetchAllJobs(@RequestBody(required = false) Long id) {
        try {
            if (id != null) {
                JobDto job = jobService.fetchJobById(id);
                if (job == null) {
                    ApiResponse response = new ApiResponse("error", "Job not found for ID: " + id, null);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                }
                ApiResponse response = new ApiResponse("ok", "Successfully fetched the job", job);
                return ResponseEntity.ok(response);
            } else {
                List<JobDto> jobs = jobService.fetchAllJobs();
                ApiResponse response = new ApiResponse("ok", "Successfully fetched jobs", jobs);
                return ResponseEntity.ok(response);
            }
        } catch (Exception e) {
            ApiResponse errorResponse = new ApiResponse("error", "Failed to fetch jobs: " + e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> deleteById(@RequestBody Long id) {
        Job job = jobService.deleteById(id);
        try {
            ApiResponse response = new ApiResponse("ok", "successfully deleted the job post", job);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (RuntimeException e) {
            ApiResponse error = new ApiResponse("error", "job post not found " + e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (Exception e) {
            ApiResponse error = new ApiResponse("error", "failed to delete the job post " + e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}