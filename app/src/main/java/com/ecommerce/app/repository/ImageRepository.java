package com.ecommerce.app.repository;

import com.ecommerce.app.model.ImageProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<ImageProduct, Integer> {
    void deleteByImagem(String imagem);
}
