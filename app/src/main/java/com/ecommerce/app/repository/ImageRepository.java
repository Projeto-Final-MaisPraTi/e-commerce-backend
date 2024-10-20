package com.ecommerce.app.repository;

import com.ecommerce.app.model.ImageProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<ImageProduct, Integer> {
    Optional<ImageProduct> findByImagem(String imagem);
}
