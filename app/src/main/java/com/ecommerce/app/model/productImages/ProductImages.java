package com.ecommerce.app.model.productImages;

import com.ecommerce.app.model.product.Product;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "imagens_produtos")
public class ProductImages {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "imagem", nullable = false)
	private String imagem;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_produto", nullable = false)
	private Product product;
	
}
