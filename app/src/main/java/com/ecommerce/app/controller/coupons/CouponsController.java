package com.ecommerce.app.controller.coupons;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ecommerce.app.dto.coupons.CouponsDTO;
import com.ecommerce.app.service.coupons.CouponsService;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/coupons")
public class CouponsController {

    @Autowired
    private CouponsService couponsService;

    @GetMapping
    public List<CouponsDTO> getAllCoupons() {
        return couponsService.getAllCoupons();
    }

    @GetMapping("/{id}")
    public CouponsDTO getCouponById(@PathVariable Integer id) {
        return couponsService.getCouponById(id);
    }

    // Para admin - cria cupom manualmente
    @PostMapping
    public CouponsDTO createCoupon(@RequestBody CouponsDTO couponsDTO) {
        return couponsService.createCoupon(couponsDTO);
    }

    @PutMapping("/{id}")
    public CouponsDTO updateCoupon(@PathVariable Integer id, @RequestBody CouponsDTO couponsDTO) {
        return couponsService.updateCoupon(id, couponsDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteCoupon(@PathVariable Integer id) {
        couponsService.deleteCoupon(id);
    }

    // Verifica a validade de um cupom
    // Retorna um cupom válido para o carrinho, se houver
    @GetMapping("/valid")
    public CouponsDTO getValidCoupon() {
        return couponsService.findValidCoupon();
    }


    // Marca o cupom como usado após uma compra
    @PostMapping("/mark-used/{codigo}")
    public void markCouponAsUsed(@PathVariable String codigo) {
        couponsService.markCouponAsUsed(codigo);
    }
}