package com.ecommerce.app.repository.productImages;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.app.model.productImages.ProductImages;

public interface ProductImagesRepository extends JpaRepository<ProductImages, Long>{

	ProductImages findByProductImages(String productImages);
	
}
