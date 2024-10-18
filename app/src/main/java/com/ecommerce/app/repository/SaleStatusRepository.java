package com.ecommerce.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.app.model.SaleStatus;

public interface SaleStatusRepository extends JpaRepository<SaleStatus, Long>{

	SaleStatus findByEstado(String estado);
	
}
