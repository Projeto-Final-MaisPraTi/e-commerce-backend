package com.ecommerce.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.app.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{
	List<Product> findByNome(String nome);
	List<Product> findByCategoria(String categoria);
}
