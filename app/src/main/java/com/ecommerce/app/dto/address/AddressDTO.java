package com.ecommerce.app.dto.address;

import com.ecommerce.app.model.address.Address;
import com.ecommerce.app.model.user.User;
import lombok.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {
	private Integer id;

	@NotBlank(message = "O endereço não pode estar em branco")
	private String endereco;

	@NotNull(message = "O número não pode ser nulo")
	private int numero;

	@NotBlank(message = "A cidade não pode estar em branco")
	private String cidade;

	@NotBlank(message = "O estado não pode estar em branco")
	@Size(min = 2, max = 2, message = "O estado deve ter 2 caracteres")
	private String uf;

	@NotBlank(message = "O CEP não pode estar em branco")
	@Pattern(regexp = "\\d{5}-\\d{3}", message = "O CEP deve estar no formato 99999-999")
	private String cep;

//	@NotNull(message = "O usuário não pode estar em branco")
	private Integer userId;

	public AddressDTO(Address address) {
		this.id = address.getId();
		this.endereco = address.getEndereco();
		this.numero = address.getNumero();
		this.cidade = address.getCidade();
		this.uf = address.getUf();
		this.cep = address.getCep();
		this.userId = address.getUser().getId();
	}
}
