package com.ecommerce.app.repository.itemCart;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.app.model.itemCart.ItemCart;

public interface ItemCartRepository extends JpaRepository<ItemCart, Integer>{
	List<ItemCart> findByUserId(Integer userId);

    Optional<ItemCart> findByUserIdAndProductId(Integer userId, Integer productId);

    void deleteAllByIdIn(List<Integer> ids);
}