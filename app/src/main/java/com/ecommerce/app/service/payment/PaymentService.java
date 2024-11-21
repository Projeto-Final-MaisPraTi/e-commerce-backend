package com.ecommerce.app.service.payment;

import com.ecommerce.app.dto.payment.PaymentDTO;
import com.ecommerce.app.model.payment.Payment;
import com.ecommerce.app.model.sales.Sales;
import com.ecommerce.app.repository.payment.PaymentRepository;
import com.ecommerce.app.repository.sales.SalesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    @Autowired
    private SalesRepository salesRepository;

    public List<PaymentDTO> getAllPayment() {
        return paymentRepository
                .findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public PaymentDTO getPaymentById(Integer id) {
        Optional<Payment> payment = paymentRepository.findById(id);

        return payment.map(this::convertToDTO).orElseThrow(() -> new RuntimeException("Tipo de pagamento não encontrado!"));
    }

    public PaymentDTO processPayment(PaymentDTO paymentDTO) {
        Payment payment = new Payment();
        payment.setTipo(paymentDTO.getTipo());
        payment.setValor(paymentDTO.getValor());
        if ("card".equalsIgnoreCase(paymentDTO.getTipo())) {
            if (validarCartao(paymentDTO)) {
                // Processar parcelamento
                processarParcelamento(paymentDTO);

                payment.setValorParcela(paymentDTO.getValorParcela());

            } else {
                throw new RuntimeException("Dados do cartão inválidos!");
            }
        }
//        else {
//            throw new RuntimeException("Tipo de pagamento inválido!");
//        }
        paymentRepository.save(payment);
        System.out.println(payment);
        Sales sale = salesRepository.findById(paymentDTO.getIdSale()).get();
        sale.setPayment(payment);
        salesRepository.save(sale);
        paymentDTO.setId(payment.getId());
        paymentDTO.setStatus("REALIZADO");
        return paymentDTO;
    }

    public PaymentDTO updatePayment(Integer id, PaymentDTO paymentDTO) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tipo de pagamento não encontrado!"));

        payment.setTipo(paymentDTO.getTipo());
        payment.setValor(paymentDTO.getValor());
        payment.setValorParcela(paymentDTO.getValorParcela());
        paymentRepository.save(payment);

        return convertToDTO(payment);
    }

    public void deletePayment(Integer id) {
        paymentRepository.deleteById(id);
    }

    private boolean validarCartao(PaymentDTO paymentDTO) {
        return validateName(paymentDTO.getCardDetails().getNomeDoDono()) &&
                validateCardNumber(paymentDTO.getCardDetails().getNumeroCartao()) &&
                validateExpiryDate(paymentDTO.getCardDetails().getValidade()) &&
                validateCVV(paymentDTO.getCardDetails().getCvc());
    }

    private boolean validateName(String name) {
        return name != null && !name.trim().isEmpty();
    }

    private boolean validateCardNumber(String number) {
        return number != null && number.matches("\\d{16}");
    }

    private boolean validateExpiryDate(String expiry) {
        return expiry != null && expiry.matches("(0[1-9]|1[0-2])/\\d{2}");
    }

    private boolean validateCVV(String cvv) {
        return cvv != null && cvv.matches("\\d{3}");
    }

    private void processarParcelamento(PaymentDTO paymentDTO) {
        if (paymentDTO.getCardDetails().getParcelas() != null && paymentDTO.getCardDetails().getParcelas() > 1) {
//            double totalComJuros = paymentDTO.getValor() * (1 + (paymentDTO.getJuros() / 100));
            double valorParcela = paymentDTO.getValor() / paymentDTO.getCardDetails().getParcelas();
            paymentDTO.setValorParcela(valorParcela);
        }
    }

    private PaymentDTO convertToDTO(Payment payment) {
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setId(payment.getId());
        paymentDTO.setTipo(payment.getTipo());
        paymentDTO.setValor(payment.getValor());
        paymentDTO.setValorParcela(payment.getValorParcela());
        // Adicione o status e outras informações se necessário
        return paymentDTO;
    }
}