package com.ecommerce.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.app.model.Reviews;

public interface ReviewsRepository extends JpaRepository<Reviews, Long>{

	Reviews findByReviews(String reviews);
	
}
