package com.ecommerce.app.model.sales;

import java.util.List;

import com.ecommerce.app.infra.enums.TypeSaleStatus;
import com.ecommerce.app.model.coupons.Coupons;
import com.ecommerce.app.model.payment.Payment;
import com.ecommerce.app.model.salesItems.SalesItems;
import com.ecommerce.app.model.user.User;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@Table(name = "vendas")
@AllArgsConstructor
@NoArgsConstructor
public class Sales {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "total", nullable = false)
	private Double total;
	
//	@ManyToOne
//	@JoinColumn(name = "id_estado", nullable = false)
//	private SaleStatus saleStatus; // "FINALIZADO", "ENVIANDO", "CANCELADO", "PENDENTE"

	@Enumerated(EnumType.STRING)
	@Column(name = "typeSaleStatus", nullable = false)
	private TypeSaleStatus typeSaleStatus; // "FINALIZADO", "ENVIANDO", "CANCELADO", "PENDENTE"
	
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
