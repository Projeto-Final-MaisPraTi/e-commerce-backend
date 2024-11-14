package com.ecommerce.app.model.payment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
@Data
@Table(name = "payment")
public class Payment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotBlank(message = "O tipo de pagamento não pode estar em branco")
	@Column(name = "type")
	private String tipo; // "DINHEIRO", "CARTÃO"

	@NotNull(message = "O valor não pode estar em branco")
	@PositiveOrZero(message = "O valor deve ser positivo")
	@Column(name = "payment_amount")
	private Double valor; // Valor total da compra

	@NotNull(message = "O valor da parcela não pode estar em branco")
	@PositiveOrZero(message = "O valor da parcela deve ser positivo")
	@Column(name = "installment_value") // Tradução: Valor da parcela
	private Double valorParcela;
}