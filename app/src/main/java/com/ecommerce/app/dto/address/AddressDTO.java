package com.ecommerce.app.dto.address;

import com.ecommerce.app.model.address.Address;
import com.ecommerce.app.model.user.User;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
public class AddressDTO {

	private Long id;
	
	private String endereco;

	private int numero;
	
	private String cidade;
	
	private String uf;
	
	private String cep;

	private Boolean endereco_padrao;

	private User user;

	public AddressDTO(Long id, String endereco, int numero, String cidade, String uf, String cep, Boolean endereco_padrao, User user) {
		this.id = id;
		this.endereco = endereco;
		this.numero = numero;
		this.cidade = cidade;
		this.uf = uf;
		this.cep = cep;
		this.endereco_padrao = endereco_padrao;
		this.user = user;
	}

	public AddressDTO(Address address) {
	}
}
