package com.ecommerce.app.controller.sales;

import java.util.List;

import com.ecommerce.app.dto.salesItems.SalesItemsDTO;
import com.ecommerce.app.infra.enums.TypeSaleStatus;
import com.ecommerce.app.model.coupons.Coupons;
import com.ecommerce.app.model.payment.Payment;
import com.ecommerce.app.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.app.dto.sales.SalesDTO;
import com.ecommerce.app.service.sales.SalesService;

@RestController
@RequestMapping("/api/sales")
public class SalesController {
    
    @Autowired
    private SalesService salesService;

    public SalesController(SalesService salesService) {
        this.salesService = salesService;
    }

    @GetMapping
    public List<SalesDTO> getAllSales() {
        return salesService.getAllSales();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalesDTO> getSalesById(@PathVariable Integer id) {
        return ResponseEntity.ok(salesService.getSaleById(id));
    }

    @PostMapping
    public ResponseEntity<SalesDTO> createSales(@RequestBody SalesDTO salesDTO, User user, Payment payment, Coupons coupons, SalesItemsDTO salesItemsDTO) {
        SalesDTO newSales = salesService.createSale(salesDTO, user, payment, coupons, salesItemsDTO);
        return ResponseEntity.status(201).body(newSales);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SalesDTO> updateSales(
            @PathVariable Integer id, @RequestBody SalesDTO salesDTO, User user, Payment payment, Coupons coupons, SalesItemsDTO salesItemsDTO) {
        return ResponseEntity.ok(salesService.updateSale(id, salesDTO, user, payment, coupons, salesItemsDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSales(@PathVariable Integer id) {
        salesService.deleteSale(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{status}")
    public List<SalesDTO> getSalesByStatus(@PathVariable TypeSaleStatus status) {
        return salesService.getSalesByStatus(status);
    }

}
