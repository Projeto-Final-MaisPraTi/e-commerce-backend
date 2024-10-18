package com.ecommerce.app.dto;

import com.ecommerce.app.model.Address;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {

	private Long id;
	
	private String endereco;
	
	private String cidade;
	
	private String uf;
	
	private String cep;

	public AddressDTO(Address address) {
		this.id = address.getId();
		this.endereco = address.getEndereco();
		this.cidade = address.getCidade();
		this.uf = address.getUf();
		this.cep = address.getCep();
	}
	
	
	
}
