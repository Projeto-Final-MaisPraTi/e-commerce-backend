package com.ecommerce.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.app.model.ProductImages;

public interface ProductImagesRepository extends JpaRepository<ProductImages, Long>{

	ProductImages findByProductImages(String productImages);
	
}
