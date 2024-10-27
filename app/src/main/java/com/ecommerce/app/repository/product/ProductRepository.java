package com.ecommerce.app.repository.product;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecommerce.app.model.product.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
	Optional<Product> findById(Long id);
	List<Product> findByNome(String nome);
	List<Product> findByCategoria(String categoria);

	// busca parcialmente por nome
	//@Query("SELECT p FROM Product p WHERE LOWER(p.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
	//List<Product> findByNomeContainingIgnoreCase(@Param("nome") String nome);

}
