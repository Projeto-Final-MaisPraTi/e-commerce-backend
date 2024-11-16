package com.ecommerce.app.repository.salesItems;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.app.model.salesItems.SalesItems;
import org.springframework.data.jpa.repository.Query;

public interface SalesItemsRepository extends JpaRepository<SalesItems, Integer>{

    @Query("select s.product.id as productId, SUM(s.quantidade) as totalSold from SalesItems s group by s.product.id order by totalSold desc")
    Page<Object[]> getBestSallers(Pageable pageable);
}
