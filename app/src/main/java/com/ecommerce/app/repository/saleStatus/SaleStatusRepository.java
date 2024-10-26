package com.ecommerce.app.repository.saleStatus;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.app.model.saleStatus.SaleStatus;

public interface SaleStatusRepository extends JpaRepository<SaleStatus, Long>{

	SaleStatus findByEstado(String estado);
	
}
