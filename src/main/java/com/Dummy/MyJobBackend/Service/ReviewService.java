package com.Dummy.MyJobBackend.Service;

import com.Dummy.MyJobBackend.Dto.ReviewDto;
import com.Dummy.MyJobBackend.Entity.Company;
import com.Dummy.MyJobBackend.Entity.Review;
import com.Dummy.MyJobBackend.Repository.CompanyRepository;
import com.Dummy.MyJobBackend.Repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private CompanyRepository companyRepository;

    public List<ReviewDto> fetchAllJobs() {
        //    return Optional.of(jobRepository.findAll()).orElse(new ArrayList<>()); this is different from below query
        List<Review> reviews =  reviewRepository != null ? reviewRepository.findAll() : Collections.emptyList();
        List<ReviewDto> dtos = new ArrayList<>();
        if(reviews!=null){
            for(Review review : reviews){
                ReviewDto dto = new ReviewDto();
                dto.setId(review.getId());
                dto.setDescription(review.getDescription());
                dto.setRating(review.getRating());
                dto.setTitle(review.getTitle());
                dto.setCompanyid(review.getCompany()!=null && review.getCompany().getId()!=null ? review.getCompany().getId() : -1L);
                dtos.add(dto);
            }
            return dtos;
        }else {
            return dtos;
        }
    }

    public Review createOrUpdate(ReviewDto dto) {
        Company company = companyRepository.findById(dto.getCompanyid()).orElseThrow(() -> new RuntimeException("Review not found by this id " + dto.getCompanyid()));
        Review review;
        if (dto.getId() != null && reviewRepository.existsById(dto.getId())) {
            review = reviewRepository.findById(dto.getId()).orElseThrow(() -> new RuntimeException("review not found for update:" + dto.getId()));
            review.setTitle(dto.getTitle());
            review.setDescription(dto.getDescription());
            review.setRating(dto.getRating());
            review.setCompany(company);
        } else {
            review = new Review();
            review.setTitle(dto.getTitle());
            review.setDescription(dto.getDescription());
            review.setRating(dto.getRating());
            review.setCompany(company);
        }
        return reviewRepository.save(review);
    }
    public Review deleteById(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found with id: " + id));
        reviewRepository.delete(review);
        return review;
    }

    public List<ReviewDto> fetchById(Long id) {
        Company company = companyRepository.findById(id).orElseThrow(()-> new RuntimeException("Company by this id does not exist"));
        List<Review> reviews = reviewRepository.findByCompanyId(id);
        List<ReviewDto> dtos = new ArrayList<>();
        for(Review review : reviews){
            ReviewDto dto = new ReviewDto();
            dto.setId(review.getId());
            dto.setTitle(review.getTitle());
            dto.setDescription(review.getDescription());
            dto.setRating(review.getRating());
            dto.setCompanyid(review.getCompany().getId());
            dtos.add(dto);
        }
        return dtos;
    }
}
