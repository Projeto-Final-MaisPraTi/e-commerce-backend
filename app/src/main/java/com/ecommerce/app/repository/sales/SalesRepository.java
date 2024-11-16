package com.ecommerce.app.repository.sales;

import java.util.List;

import com.ecommerce.app.infra.enums.TypeSaleStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.app.model.sales.Sales;

public interface SalesRepository extends JpaRepository<Sales, Integer>{

	List<Sales> findByUserId(Integer userId);
//	List<Sales> findBySaleStatusEstado(String estado); // "FINALIZADO", "ENVIANDO", "CANCELADO", "PENDENTE"

//	void updatedSaleStatus(Long id, TypeSaleStatus typeSaleStatus);
	List<Sales> findByTypeSaleStatus(TypeSaleStatus typeSaleStatus);
}
