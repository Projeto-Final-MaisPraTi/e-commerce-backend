package com.ecommerce.app.repository.reviews;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.app.model.reviews.Reviews;

import java.util.List;
import java.util.Optional;

public interface ReviewsRepository extends JpaRepository<Reviews, Integer> {
	List<Reviews> findByProductId(Integer productId);
	Optional<Reviews> findByProductIdAndUserId(Integer productId, Integer userId);
}