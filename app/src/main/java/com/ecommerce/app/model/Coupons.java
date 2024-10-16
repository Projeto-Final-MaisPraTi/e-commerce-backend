package com.ecommerce.app.model;

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
@Table(name = "cupons")
public class Coupons {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "codigo", nullable = false)
	private String codigo;
	
	@Column(name = "desconto_porcentagem", nullable = false)
	private Double desconto_porcentagem;
	
	@Column(name = "data_inicial")
	private LocalDate data_inicial;
	
	@Column(name = "data_final")
	private LocalDate data_final;
	
	@Column(name = "expiracao")
	private Boolean expiracao;
	
}
