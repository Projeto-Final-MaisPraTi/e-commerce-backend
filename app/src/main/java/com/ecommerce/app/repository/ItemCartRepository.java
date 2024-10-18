package com.ecommerce.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.app.model.ItemCart;

public interface ItemCartRepository extends JpaRepository<ItemCart, Long>{

	List<ItemCart> findByUserId(Integer userId);
	
}
