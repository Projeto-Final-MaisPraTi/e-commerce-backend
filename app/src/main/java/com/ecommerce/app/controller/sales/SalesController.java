package com.ecommerce.app.controller.sales;

import java.util.List;
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
    public ResponseEntity<SalesDTO> getSalesById(@PathVariable Long id) {
        return ResponseEntity.ok(salesService.getSaleById(id));
    }

    @PostMapping
    public ResponseEntity<SalesDTO> createSales(@RequestBody SalesDTO salesDTO) {
        SalesDTO newSales = salesService.createSale(salesDTO);
        return ResponseEntity.status(201).body(newSales);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SalesDTO> updateSales(
            @PathVariable Long id, @RequestBody SalesDTO salesDTO) {
        return ResponseEntity.ok(salesService.updateSale(id, salesDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSales(@PathVariable Long id) {
        salesService.deleteSale(id);
        return ResponseEntity.noContent().build();
    }

}
