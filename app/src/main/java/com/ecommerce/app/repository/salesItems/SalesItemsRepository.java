package com.ecommerce.app.repository.salesItems;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.app.model.salesItems.SalesItems;

public interface SalesItemsRepository extends JpaRepository<SalesItems, Long>{
	
}
