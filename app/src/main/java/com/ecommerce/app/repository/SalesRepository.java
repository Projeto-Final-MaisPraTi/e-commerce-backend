package com.ecommerce.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.app.model.Sales;

public interface SalesRepository extends JpaRepository<Sales, Long>{

	List<Sales> findByUserId(Integer userId);
	List<Sales> findBySaleStatusEstado(String estado); // "FINALIZADO", "ENVIANDO", "CANCELADO", "PENDENTE"
	
}
