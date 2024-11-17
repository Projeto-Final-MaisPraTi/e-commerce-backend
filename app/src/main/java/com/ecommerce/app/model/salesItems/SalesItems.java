package com.ecommerce.app.model.salesItems;

import com.ecommerce.app.model.product.Product;
import com.ecommerce.app.model.sales.Sales;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
@Data
@Table(name = "sales_itens")
public class SalesItems {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotNull(message = "A venda não pode estar em branco")
	@ManyToOne
	@JoinColumn(name = "sale_id")
	private Sales sales;

	@NotNull(message = "O produto não pode estar em branco")
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;

	@NotNull(message = "A quantidade não pode estar em branco")
	@Positive(message = "A quantidade deve ser maior que zero")
	@Column(name = "quantity")
	private Integer quantidade;

	@NotNull(message = "O preço não pode estar em branco")
	@PositiveOrZero(message = "O preço deve ser zero ou positivo")
	@Column(name = "price")
	private Double preco;
}