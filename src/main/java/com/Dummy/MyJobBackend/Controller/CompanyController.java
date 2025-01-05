package com.Dummy.MyJobBackend.Controller;

import com.Dummy.MyJobBackend.Dto.ApiResponse;
import com.Dummy.MyJobBackend.Dto.CompanyDto;
import com.Dummy.MyJobBackend.Dto.JobDto;
import com.Dummy.MyJobBackend.Entity.Company;
import com.Dummy.MyJobBackend.Entity.Job;
import com.Dummy.MyJobBackend.Service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/company")
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createOrUpdate(@RequestBody CompanyDto Dto) {
        System.out.println(Dto.getName() + " "+Dto.getDescription());
        Company company = companyService.createOrUpdate(Dto);
        try {
            ApiResponse response = new ApiResponse("ok", "successfully created the company", company);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (RuntimeException e) {
            ApiResponse error = new ApiResponse("error", "comapany not found for update" + e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (Exception e) {
            ApiResponse error = new ApiResponse("error", "failed to create the company " + e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @GetMapping("/fetch")
    public ResponseEntity<ApiResponse> fetchAllJobs(@RequestBody(required = false) Long id) {
        try {
            if (id != null) {
                Company company = companyService.fetchJobById(id);
                if (company == null) {
                    ApiResponse response = new ApiResponse("error", "Company not found for ID: " + id, null);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                }
                ApiResponse response = new ApiResponse("ok", "Successfully fetched the Company", company);
                return ResponseEntity.ok(response);
            } else {
                List<Company> companies = companyService.fetchAllJobs();
                ApiResponse response = new ApiResponse("ok", "Successfully fetched Companies", companies);
                return ResponseEntity.ok(response);
            }
        } catch (Exception e) {
            ApiResponse errorResponse = new ApiResponse("error", "Failed to fetch Companies: " + e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> deleteById(@RequestBody Long id) {
        Company company = companyService.deleteById(id);
        try {
            ApiResponse response = new ApiResponse("ok", "successfully deleted the Company by id : "+ id, company);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (RuntimeException e) {
            ApiResponse error = new ApiResponse("error", "Company not found by id : "+id+" : " + e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (Exception e) {
            ApiResponse error = new ApiResponse("error", "failed to delete the Company " + e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

}
