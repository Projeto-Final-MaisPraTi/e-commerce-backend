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
        Optional<Coupons> coupon = couponsRepository.findById(id);

        return coupon.map(this::convertToDTO).orElseThrow(() -> new RuntimeException("Cupom não encontrado!"));
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
        Coupons coupons = couponsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cupom não encontrado!"));

        coupons.setCodigo(couponsDTO.getCodigo());
        coupons.setDesconto_porcentagem(couponsDTO.getDesconto());
        couponsRepository.save(coupons);

        return convertToDTO(coupons);
    }

    public void deleteCoupon(Integer id) {
        couponsRepository.deleteById(id);
    }

    // Verificar validade do cupom
    public boolean isCouponValid(String codigo) {
        Coupons coupon = couponsRepository.findByCodigo(codigo);
        return coupon != null && coupon.getAtivo() && !coupon.getUsado() && coupon.getData_final().isAfter(LocalDate.now());
    }

    // Gerar cupons automaticamente
    @Scheduled(cron = "0 0 0 */4 * ?")
    public void generateCouponAutomatically() {
        Coupons coupon = new Coupons();
        coupon.setCodigo("DESCONTO" + UUID.randomUUID().toString().substring(0, 8));
        coupon.setDesconto_porcentagem((double) (5 + new Random().nextInt(11))); // entre 5% e 15%
        coupon.setData_inicial(LocalDate.now());
        coupon.setData_final(LocalDate.now().plusDays(2));
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