package com.ecommerce.app.repository.productImages;

import com.ecommerce.app.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.app.model.productImages.ProductImages;

import java.util.List;

public interface ProductImagesRepository extends JpaRepository<ProductImages, Long>{

	ProductImages findByImagem(String imagem);
//	List<ProductImages> findByProduct(Product product); // obtém todas as imagens relacionadas a um produto
//	List<ProductImages> findByProduct_Id(Long productId); // busca pelo ID do produto

}
