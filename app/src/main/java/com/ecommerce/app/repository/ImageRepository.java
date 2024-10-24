package com.ecommerce.app.repository;

import com.ecommerce.app.model.ImageProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ImageRepository extends JpaRepository<ImageProduct, Integer> {
    @Query("SELECT i FROM ImageProduct i WHERE i.imagem = :imagem")
    Optional<ImageProduct> findByImagem(@Param("imagem") String imagem);

    @Query("select i from ImageProduct i where i.product.id = :id and i.capaProduto = true")
    Optional<ImageProduct> getCoverByProductId(@Param("id") Integer id);

    @Query("select i from ImageProduct i where i.product.id = :id and i.capaProduto = false")
    List<ImageProduct> getImagesByProductId(@Param("id") Integer id);
}
