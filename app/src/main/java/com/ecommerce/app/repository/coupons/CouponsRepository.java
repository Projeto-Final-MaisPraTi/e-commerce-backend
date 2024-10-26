package com.ecommerce.app.repository.coupons;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.app.model.coupons.Coupons;

public interface CouponsRepository extends JpaRepository<Coupons, Long>{

	Coupons findByCodigo(String codigo);
	
}
