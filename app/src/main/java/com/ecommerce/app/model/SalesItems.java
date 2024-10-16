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
@Table(name = "itens_vendas")
public class SalesItems {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "id_venda")
	private Sales sales;
	
	@ManyToOne
	@JoinColumn(name = "id_produto")
	private Product product;
	
	@Column(name = "quantidade")
	private Integer quantidade;
	
	@Column(name = "total_item")
	private Double total_item;
	
}
