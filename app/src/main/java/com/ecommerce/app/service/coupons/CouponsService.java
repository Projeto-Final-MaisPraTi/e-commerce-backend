package com.ecommerce.app.service.coupons;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.Optional;
import java.time.LocalDate;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.ecommerce.app.dto.coupons.CouponsDTO;
import com.ecommerce.app.model.coupons.Coupons;
import com.ecommerce.app.repository.coupons.CouponsRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CouponsService {

    private final CouponsRepository couponsRepository;

    public List<CouponsDTO> getAllCoupons() {
        return couponsRepository
                .findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public CouponsDTO getCouponById(Integer id) {
        Coupons coupon = couponsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cupom não encontrado!"));
        return convertToDTO(coupon);
    }

    public CouponsDTO createCoupon(CouponsDTO couponsDTO) {
        Coupons coupon = new Coupons();
        coupon.setCodigo(couponsDTO.getCodigo());
        coupon.setDesconto_porcentagem(couponsDTO.getDesconto());
        coupon.setData_inicial(LocalDate.now());
        coupon.setData_final(LocalDate.now().plusDays(2)); // duração de 2 dias
        coupon.setAtivo(true);
        coupon.setUsado(false);

        couponsRepository.save(coupon);
        return convertToDTO(coupon);
    }

    public CouponsDTO updateCoupon(Integer id, CouponsDTO couponsDTO) {
        Coupons coupon = couponsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cupom não encontrado!"));

        coupon.setCodigo(couponsDTO.getCodigo());
        coupon.setDesconto_porcentagem(couponsDTO.getDesconto());
        couponsRepository.save(coupon);

        return convertToDTO(coupon);
    }

    public void deleteCoupon(Integer id) {
        couponsRepository.deleteById(id);
    }

    // Verifica a validade do cupom
    public CouponsDTO findValidCoupon() {
        Optional<Coupons> coupon = couponsRepository.findFirstByAtivoTrueAndUsadoFalseAndData_finalAfter(LocalDate.now());
        return coupon.map(this::convertToDTO).orElse(null);
    }

    // Marca o cupom como usado após a compra
    public void markCouponAsUsed(String codigo) {
        Coupons coupon = couponsRepository.findByCodigo(codigo);
        if (coupon != null) {
            coupon.setUsado(true);
            coupon.setAtivo(false);
            couponsRepository.save(coupon);
        }
    }

    // Geração automática de cupons
    @Scheduled(cron = "0 0 0 */4 * ?") // Executa a cada 4 dias
    public void generateCouponAutomatically() {
        Coupons coupon = new Coupons();
        coupon.setCodigo("DESCONTO" + UUID.randomUUID().toString().substring(0, 8));
        coupon.setDesconto_porcentagem((double) (5 + new Random().nextInt(11))); // 5% a 15%
        coupon.setData_inicial(LocalDate.now());
        coupon.setData_final(LocalDate.now().plusDays(2)); // Cupom válido por 2 dias
        coupon.setAtivo(true);
        coupon.setUsado(false);
        couponsRepository.save(coupon);
    }

    private CouponsDTO convertToDTO(Coupons coupons) {
        return CouponsDTO.builder()
                .id(coupons.getId())
                .codigo(coupons.getCodigo())
                .desconto(coupons.getDesconto_porcentagem())
                .data_inicial(coupons.getData_inicial())
                .data_final(coupons.getData_final())
                .expirado(!coupons.getAtivo() || coupons.getUsado())
                .build();
    }
}