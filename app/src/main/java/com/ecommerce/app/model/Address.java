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
@Table(name = "endereco")
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "endereco", columnDefinition = "TEXT")
	private String endereco;
	
	@Column(name = "numero", nullable = false)
	private int numero;
	
	@Column(name = "cidade", nullable = false)
	private String cidade;
	
	@Column(name = "uf", nullable = false)
	private String uf;
	
	@Column(name = "cep", nullable = false)
	private String cep;
	
	@Column(name = "endereco_padrao")
	private boolean endereco_padrao;
	
	@ManyToOne
	@JoinColumn(name = "id_usuario", nullable = false)
	private User user;
	
}
