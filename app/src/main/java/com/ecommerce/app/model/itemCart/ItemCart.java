package com.ecommerce.app.model.itemCart;

import com.ecommerce.app.model.product.Product;
import com.ecommerce.app.model.user.User;

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
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import lombok.ToString;

@Entity
@Data
@Table(name = "cart_itens")
@ToString(exclude = {"user", "product"})
public class ItemCart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotNull(message = "A quantidade não pode estar em branco")
	@Min(value = 1, message = "A quantidade deve ser pelo menos 1")
	@Max(value = 100, message = "A quantidade máxima permitida é 100")
	@Column(name = "quantity")
	private Integer quantidade;

	@NotNull(message = "O produto não pode estar em branco")
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;

	@NotNull(message = "O usuário não pode estar em branco")
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
}