package com.ecommerce.app.model.saleStatus;

import com.ecommerce.app.infra.enums.TypeSaleStatus;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "estado_venda")
public class SaleStatus {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(name = "estado", nullable = false, unique = true)
	//private String estado; // "FINALIZADO", "ENVIANDO", "CANCELADO", "PENDENTE"
	private TypeSaleStatus estado;
	
}
