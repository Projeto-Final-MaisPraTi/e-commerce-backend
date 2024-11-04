package com.ecommerce.app.repository.productImages;

import com.ecommerce.app.model.productImages.ProductImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductImagesRepository extends JpaRepository<ProductImages, Integer> {
    @Query("SELECT i FROM ProductImages i WHERE i.imagem = :imagem")
    Optional<ProductImages> findByImagem(@Param("imagem") String imagem);

    @Query("select i from ProductImages i where i.product.id = :id and i.capaProduto = true")
    Optional<ProductImages> getCoverByProductId(@Param("id") Integer id);

    @Query("select i from ProductImages i where i.product.id = :id and i.capaProduto = false")
    List<ProductImages> getImagesByProductId(@Param("id") Integer id);
}
