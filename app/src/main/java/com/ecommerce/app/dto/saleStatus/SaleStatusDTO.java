package com.ecommerce.app.dto.saleStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaleStatusDTO {

	private Long id;
	private String estado; // "FINALIZADO", "ENVIANDO", "CANCELADO", "PENDENTE"
	
}
