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
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
import lombok.ToString;

@Entity
@Data
@Table(name = "addresses")
@ToString(exclude = {"user"})
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotBlank(message = "O endereço não pode estar em branco")
	@Column(name = "address", columnDefinition = "TEXT")
	private String endereco;

	@NotNull(message = "O número não pode ser nulo")
	@Column(name = "numbers", nullable = false)
	private int numero;

	@NotBlank(message = "A cidade não pode estar em branco")
	@Column(name = "city", nullable = false)
	private String cidade;

	@NotBlank(message = "O estado não pode estar em branco")
	@Size(min = 2, max = 2, message = "O estado deve ter 2 caracteres")
	@Column(name = "uf", nullable = false)
	private String uf;

	@NotBlank(message = "O CEP não pode estar em branco")
	@Pattern(regexp = "\\d{5}-\\d{3}", message = "O CEP deve estar no formato 99999-999")
	@Column(name = "zipcode", nullable = false)
	private String cep;

	@ManyToOne()
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
}