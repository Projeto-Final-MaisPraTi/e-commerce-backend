package com.ecommerce.app.repository.salesItems;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.app.model.salesItems.SalesItems;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SalesItemsRepository extends JpaRepository<SalesItems, Integer> {
    @Query("SELECT si FROM SalesItems si WHERE si.sales.user.id = :userId")
    List<SalesItems> findByUserId(@Param("userId") Integer userId);
}