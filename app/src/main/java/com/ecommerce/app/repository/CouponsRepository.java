package com.ecommerce.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.app.model.Coupons;

public interface CouponsRepository extends JpaRepository<Coupons, Long>{

	Coupons findByCodigo(String codigo);
	
}
