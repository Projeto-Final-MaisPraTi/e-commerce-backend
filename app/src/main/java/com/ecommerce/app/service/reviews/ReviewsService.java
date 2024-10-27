package com.ecommerce.app.service.reviews;

import com.ecommerce.app.dto.reviews.ReviewsDTO;
import com.ecommerce.app.model.product.Product;
import com.ecommerce.app.model.reviews.Reviews;
import com.ecommerce.app.model.user.User;
import com.ecommerce.app.repository.product.ProductRepository;
import com.ecommerce.app.repository.reviews.ReviewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewsService {

    private final ReviewsRepository reviewsRepository;
    private final ProductRepository productRepository;

    public List<ReviewsDTO> getAllReviews() {
        return reviewsRepository
                .findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ReviewsDTO getReviewById(Long id) {
        Optional<Reviews> reviews = reviewsRepository.findById(id);

        return reviews.map(this::convertToDTO).orElseThrow(() -> new RuntimeException("Review não encontrada!"));
    }

    public ReviewsDTO createReview(ReviewsDTO reviewsDTO, Product product, User user) {
        Reviews reviewProd = reviewsRepository.findById(reviewsDTO.getId_produto())
                .orElseThrow(() -> new RuntimeException("Review não encontrada!"));

        Reviews reviews = new Reviews();
        reviews.setAvaliacao(reviewsDTO.getAvaliacao());
        reviews.setProduct(product);
        reviews.setUser(user);

        reviewsRepository.save(reviews);

        return convertToDTO(reviews);
    }

    public ReviewsDTO updateReview(Long id, ReviewsDTO reviewsDTO, Product product, User user) {
        Reviews reviews = reviewsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review não encontrada!"));

        reviews.setAvaliacao(reviewsDTO.getAvaliacao());
        reviews.setProduct(product);
        reviews.setUser(user);

        reviewsRepository.save(reviews);

        return convertToDTO(reviews);
    }

    public void deleteReview(Long id) {
        Reviews reviews = reviewsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review não encontrada!"));

        reviewsRepository.deleteById(id);
    }

    private ReviewsDTO convertToDTO(Reviews reviews) {
        ReviewsDTO reviewsDTO = new ReviewsDTO();
        reviewsDTO.setId(reviewsDTO.getId());
        reviewsDTO.setAvaliacao(reviewsDTO.getAvaliacao());

        return reviewsDTO;
    }
    
}
