package com.ecommerce.app.dto.payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardDetailsDTO {
//    @NotBlank(message = "O nome do dono não pode estar em branco")
    private String nomeDoDono;

//    @NotBlank(message = "O número do cartão não pode estar em branco")
//    @Pattern(regexp = "\\d{16}", message = "O número do cartão deve ter 16 dígitos")
    private String numeroCartao;

//    @NotBlank(message = "A validade não pode estar em branco")
//    @Pattern(regexp = "(0[1-9]|1[0-2])/\\d{2}", message = "A validade deve estar no formato MM/AA")
    private String validade;

//    @NotBlank(message = "O CVC não pode estar em branco")
//    @Pattern(regexp = "\\d{3}", message = "O CVC deve ter 3 dígitos")
    private String cvc;


//    @NotNull(message = "O número de parcelas não pode estar em branco")
//    @Min(value = 1, message = "O número de parcelas deve ser pelo menos 1")
//    @Max(value = 12, message = "O número máximo de parcelas é 12")
    private Integer parcelas; // Número de parcelas

//    @NotNull(message = "O valor do juros não pode estar em branco")
//    @PositiveOrZero(message = "O valor do juros não pode ser negativo")
//    private Double juros; // Juros
}
