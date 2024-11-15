package com.ecommerce.app.repository.coupons;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.app.model.coupons.Coupons;

import java.time.LocalDate;
import java.util.Optional;

public interface CouponsRepository extends JpaRepository<Coupons, Integer>{
	Coupons findByCodigo(String codigo);
	Optional<Coupons> findFirstByAtivoTrueAndUsadoFalseAndData_finalAfter(LocalDate dataAtual);

}