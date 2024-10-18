package com.ecommerce.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.app.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long>{
}