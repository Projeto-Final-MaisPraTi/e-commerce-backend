package com.ecommerce.app.repository.reviews;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.app.model.reviews.Reviews;

public interface ReviewsRepository extends JpaRepository<Reviews, Long>{

	Reviews findByReviews(String reviews);
	
}
