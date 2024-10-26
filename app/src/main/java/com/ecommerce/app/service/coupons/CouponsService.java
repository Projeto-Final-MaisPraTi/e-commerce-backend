package com.ecommerce.app.service.coupons;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;
import java.time.LocalDate;

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

    public CouponsDTO getCouponById(Long id) {
        Optional<Coupons> coupon = couponsRepository.findById(id);

        return coupon.map(this::convertToDTO).orElseThrow(() -> new RuntimeException("Cupom não encontrado!"));
    }

    public CouponsDTO createCoupon(CouponsDTO couponsDTO) {
        Coupons coupon = new Coupons();
        coupon.setCodigo(couponsDTO.getCodigo());
        coupon.setDesconto_porcentagem(couponsDTO.getDesconto());
        // coupon.setData_inicial(couponsDTO.getData_inicial());
        coupon.setData_inicial(LocalDate.now());
        coupon.setData_final(LocalDate.now().plusDays(30)); // cupom válido por 30 dias
        coupon.setExpiracao(false);

        couponsRepository.save(coupon);

        return convertToDTO(coupon);
    }

    public CouponsDTO updateCoupon(Long id, CouponsDTO couponsDTO) {
        //Optional<Coupons> couponOptional = couponsRepository.findById(id);
        Coupons coupons = couponsRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Cupom não encontrado!"));

        coupons.setCodigo(couponsDTO.getCodigo());
        coupons.setDesconto_porcentagem(couponsDTO.getDesconto());
        coupons.setData_inicial(coupons.getData_inicial());
        coupons.setData_final(coupons.getData_final());
        coupons.setExpiracao(coupons.getExpiracao());
        couponsRepository.save(coupons);

        return convertToDTO(coupons);
    }

    public void deleteCoupon(Long id) {
        couponsRepository.deleteById(id);
    }

    private CouponsDTO convertToDTO(Coupons coupons) {
        CouponsDTO couponsDTO = new CouponsDTO();
        couponsDTO.setId(coupons.getId());
        couponsDTO.setCodigo(coupons.getCodigo());
        couponsDTO.setDesconto(coupons.getDesconto_porcentagem());

        return couponsDTO;
    }

}
