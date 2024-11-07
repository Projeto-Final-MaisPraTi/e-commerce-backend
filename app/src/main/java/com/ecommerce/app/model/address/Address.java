package com.ecommerce.app.model.address;

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

@Entity
@Data
@Table(name = "addresses")
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "address", columnDefinition = "TEXT")
	private String endereco;
	
	@Column(name = "numbers", nullable = false)
	private int numero;
	
	@Column(name = "city", nullable = false)
	private String cidade;
	
	@Column(name = "uf", nullable = false)
	private String uf;
	
	@Column(name = "zipcode", nullable = false)
	private String cep;
	
	@Column(name = "default_address")
	private boolean endereco_padrao;
	
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
}
