package com.ecommerce.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.app.model.SalesItems;

public interface SalesItemsRepository extends JpaRepository<SalesItems, Long>{
	
}
