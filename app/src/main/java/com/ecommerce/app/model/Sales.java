package com.ecommerce.app.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "vendas")
public class Sales {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "total", nullable = false)
	private Double total;
	
	@ManyToOne
	@JoinColumn(name = "id_estado", nullable = false)
	private SaleStatus saleStatus; // "FINALIZADO", "ENVIANDO", "CANCELADO", "PENDENTE"
	
	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "id_pagamento", nullable = false)
	private Payment payment; // "DINHEIRO", "CARTÃO"
	
	@ManyToOne
	@JoinColumn(name = "id_codigo")
	private Coupons coupons;
	
	@OneToMany(mappedBy = "sales", cascade = CascadeType.ALL)
	private List<SalesItems> salesItems;
	
}
