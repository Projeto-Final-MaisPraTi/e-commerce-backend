package com.ecommerce.app.controller.coupons;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.app.dto.coupons.CouponsDTO;
import com.ecommerce.app.service.coupons.CouponsService;

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

}
