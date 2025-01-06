package com.Dummy.MyJobBackend.Controller;

import com.Dummy.MyJobBackend.Dto.ApiResponse;
import com.Dummy.MyJobBackend.Dto.ReviewDto;
import com.Dummy.MyJobBackend.Entity.Review;
import com.Dummy.MyJobBackend.Service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/review")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createOrUpdate(@RequestBody ReviewDto dto) {
        Review review = reviewService.createOrUpdate(dto);
        try {
            ApiResponse response = new ApiResponse("ok", "successfully created the review ", review);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (RuntimeException e) {
            ApiResponse error = new ApiResponse("error", "review not found for update" + e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (Exception e) {
            ApiResponse error = new ApiResponse("error", "failed to create the review  " + e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @GetMapping("/fetch")
    public ResponseEntity<ApiResponse> fetchAllJobs(@RequestBody(required = false) Long companyId) {
        try {
            if (companyId != null) {
                List<ReviewDto> review = reviewService.fetchById(companyId);
                if (review == null) {
                    ApiResponse response = new ApiResponse("error", "Job not found for ID: " + companyId, null);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                }
                ApiResponse response = new ApiResponse("ok", "Successfully fetched the review", review);
                return ResponseEntity.ok(response);
            } else {
                List<ReviewDto> reviews = reviewService.fetchAllJobs();
                ApiResponse response = new ApiResponse("ok", "Successfully fetched reviews", reviews);
                return ResponseEntity.ok(response);
            }
        } catch (Exception e) {
            ApiResponse errorResponse = new ApiResponse("error", "Failed to fetch reviews: " + e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> deleteById(@RequestBody Long id) {
        Review review = reviewService.deleteById(id);
        try {
            ApiResponse response = new ApiResponse("ok", "successfully deleted the review", review);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (RuntimeException e) {
            ApiResponse error = new ApiResponse("error", "review not found " + e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (Exception e) {
            ApiResponse error = new ApiResponse("error", "failed to delete the review " + e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}
