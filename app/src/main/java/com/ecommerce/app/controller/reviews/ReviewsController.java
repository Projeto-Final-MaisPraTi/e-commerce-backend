package com.ecommerce.app.controller.reviews;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.app.dto.reviews.ReviewsDTO;
import com.ecommerce.app.service.reviews.ReviewsService;

@RestController
@RequestMapping("/api/reviews")
public class ReviewsController {
    
    @Autowired
    private ReviewsService reviewsService;

    @GetMapping
    public List<ReviewsDTO> getAllReviews() {
        return reviewsService.getAllReviews();
    }

    @GetMapping("/{id}")
    public ReviewsDTO getReviewById(@PathVariable Long id) {
        return reviewsService.getReviewById(id);
    }

    @PostMapping
    public ReviewsDTO createReview(@RequestBody ReviewsDTO reviewsDTO) {
        return reviewsService.createReview(reviewsDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable Long id) {
        reviewsService.deleteReview(id);
    }

}
