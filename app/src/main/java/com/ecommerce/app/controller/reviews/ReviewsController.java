package com.ecommerce.app.controller.reviews;

import java.util.List;

import com.ecommerce.app.model.product.Product;
import com.ecommerce.app.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public ReviewsDTO createReview(@RequestBody ReviewsDTO reviewsDTO, Product product, User user) {
        return reviewsService.createReview(reviewsDTO, product, user);
    }

    @PutMapping("/{id}")
    public ReviewsDTO updateReview(@PathVariable Long id, @RequestBody ReviewsDTO reviewsDTO, Product product, User user) {
        return reviewsService.updateReview(id, reviewsDTO, product, user);
    }

    @DeleteMapping("/{id}")
    public void deleteReviews(@PathVariable Long id) {
        reviewsService.deleteReview(id);
    }

}
