package com.ecommerce.app.repository.payment;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.app.model.payment.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long>{
}