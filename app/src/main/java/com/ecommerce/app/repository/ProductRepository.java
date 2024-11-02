package com.ecommerce.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ecommerce.app.model.Product;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {
    @Query(value = "SELECT p FROM Product p WHERE p.nome LIKE LOWER(CONCAT('%', :nome, '%'))")
    List<Product> findByNome(String nome);

    @Query("select p from Product p where p.categoria = :category")
    List<Product> findByCategory(String category);
}
