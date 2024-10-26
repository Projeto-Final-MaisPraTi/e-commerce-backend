package com.ecommerce.app.service.payment;

import com.ecommerce.app.dto.payment.PaymentDTO;
import com.ecommerce.app.model.payment.Payment;
import com.ecommerce.app.repository.payment.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public List<PaymentDTO> getAllPayment() {
        return paymentRepository
                .findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public PaymentDTO getPaymentById(Long id) {
        Optional<Payment> payment = paymentRepository.findById(id);

        return payment.map(this::convertToDTO).orElseThrow(() -> new RuntimeException("Tipo de pagamento não encontrado!"));
    }

    public PaymentDTO createPayment(PaymentDTO paymentDTO) {
        Payment payment = new Payment();
        payment.setTipo(paymentDTO.getTipo());

        paymentRepository.save(payment);

        return convertToDTO(payment);
    }

    public PaymentDTO updatePayment(Long id, PaymentDTO paymentDTO) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tipo de pagamento não encontrado!"));

        payment.setTipo(paymentDTO.getTipo());
        paymentRepository.save(payment);

        return convertToDTO(payment);
    }

    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }

    private PaymentDTO convertToDTO(Payment payment) {
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setId(payment.getId());
        paymentDTO.setTipo(payment.getTipo());

        return paymentDTO;
    }
    
}
