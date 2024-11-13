package com.ecommerce.app.model.payment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "payment")
public class Payment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "type")
	private String tipo; // "DINHEIRO", "CARTÃO"

	@Column(name = "payment_amount")
	private Double valor; // Valor total da compra

	@Column(name = "installment_value") // Tradução: Valor da parcela
	private Double valorParcela;
}