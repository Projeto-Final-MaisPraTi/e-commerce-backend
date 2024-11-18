package com.ecommerce.app.controller.reviews;

import java.util.List;

import com.ecommerce.app.model.product.Product;
import com.ecommerce.app.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ecommerce.app.dto.reviews.ReviewsDTO;
import com.ecommerce.app.service.reviews.ReviewsService;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/reviews")
public class ReviewsController {

    @Autowired
    private ReviewsService reviewsService;

    @GetMapping
    public List<ReviewsDTO> getAllReviews() {
        return reviewsService.getAllReviews();
    }

    @GetMapping("/{productId}")
    public List<ReviewsDTO> getReviewsByProductId(@PathVariable Integer productId) {
        return reviewsService.getReviewsByProductId(productId);
    }

    @PostMapping
    public ReviewsDTO createOrUpdateReview(@RequestBody ReviewsDTO reviewsDTO) {
        return reviewsService.createOrUpdateReview(reviewsDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable Integer id) {
        reviewsService.deleteReview(id);
    }
}