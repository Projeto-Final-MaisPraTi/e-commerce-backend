package com.ecommerce.app.model.sales;

import java.util.List;

import com.ecommerce.app.infra.enums.TypeSaleStatus;
import com.ecommerce.app.model.address.Address;
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
@Table(name = "sales")
@AllArgsConstructor
@NoArgsConstructor
public class Sales {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "total", nullable = false)
	private Double total;

	@Enumerated(EnumType.STRING)
	@Column(name = "typeSaleStatus", nullable = false)
	private TypeSaleStatus typeSaleStatus; // "FINALIZADO", "ENVIANDO", "CANCELADO", "PENDENTE"

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "payment_id")
	private Payment payment; // "DINHEIRO", "CARTÃO"

	@ManyToOne
	@JoinColumn(name = "code_id")
	private Coupons coupons;

	@OneToMany(mappedBy = "sales", cascade = CascadeType.ALL)
	private List<SalesItems> salesItems;

	@ManyToOne
	@JoinColumn(name = "address_id")
	private Address address;

	private boolean activeOrder;
}