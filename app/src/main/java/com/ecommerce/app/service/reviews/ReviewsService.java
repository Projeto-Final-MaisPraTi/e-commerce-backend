package com.ecommerce.app.service.reviews;

import com.ecommerce.app.dto.reviews.ReviewsDTO;
import com.ecommerce.app.model.product.Product;
import com.ecommerce.app.model.reviews.Reviews;
import com.ecommerce.app.model.user.User;
import com.ecommerce.app.repository.product.ProductRepository;
import com.ecommerce.app.repository.reviews.ReviewsRepository;
import com.ecommerce.app.repository.user.UserRepository;
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
    private final UserRepository userRepository;

    public List<ReviewsDTO> getAllReviews() {
        return reviewsRepository
                .findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ReviewsDTO> getReviewsByProductId(Integer productId) {
        return reviewsRepository.findByProductId(productId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ReviewsDTO createOrUpdateReview(ReviewsDTO reviewsDTO) {
        Product product = productRepository.findById(reviewsDTO.getProductId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado!"));
        User user = userRepository.findById(reviewsDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        Optional<Reviews> existingReview = reviewsRepository.findByProductIdAndUserId(reviewsDTO.getProductId(), reviewsDTO.getUserId());
        Reviews reviews;

        if (existingReview.isPresent()) {
            reviews = existingReview.get();
            reviews.setAvaliacao(reviewsDTO.getAvaliacao());
        } else {
            reviews = new Reviews();
            reviews.setAvaliacao(reviewsDTO.getAvaliacao());
            reviews.setProduct(product);
            reviews.setUser(user);
        }

        reviewsRepository.save(reviews);
        return convertToDTO(reviews);
    }

    public void deleteReview(Integer id) {
        Reviews reviews = reviewsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review não encontrada!"));

        reviewsRepository.deleteById(id);
    }

    private ReviewsDTO convertToDTO(Reviews reviews) {
        ReviewsDTO reviewsDTO = new ReviewsDTO();
        reviewsDTO.setId(reviews.getId());
        reviewsDTO.setAvaliacao(reviews.getAvaliacao());
        reviewsDTO.setProductId(reviews.getProduct().getId());
        reviewsDTO.setUserId(reviews.getUser().getId());
        reviewsDTO.setUsername(reviews.getUser().getUsername());

        return reviewsDTO;
    }
}