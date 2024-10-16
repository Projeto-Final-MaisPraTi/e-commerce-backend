package com.ecommerce.app.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "carrinho_itens")
public class ItemCart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "quantidade")
	private Integer quantidade;
	
	@ManyToOne
	@JoinColumn(name = "id_produto")
	private Product product;
	
	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private User user;
	
}
