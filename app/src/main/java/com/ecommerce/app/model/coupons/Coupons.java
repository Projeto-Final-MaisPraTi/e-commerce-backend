package com.ecommerce.app.model.coupons;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "coupons")
public class Coupons {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "code", nullable = false)
	private String codigo;
	
	@Column(name = "discount_percentage", nullable = false)
	private Double desconto_porcentagem;
	
	@Column(name = "initial_date")
	private LocalDate data_inicial;
	
	@Column(name = "final_date")
	private LocalDate data_final;
	
	@Column(name = "expiration")
	private Boolean expiracao;
	
}
