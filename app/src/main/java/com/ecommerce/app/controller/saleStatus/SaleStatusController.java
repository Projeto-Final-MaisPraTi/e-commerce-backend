package com.ecommerce.app.controller.saleStatus;

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

import com.ecommerce.app.dto.saleStatus.SaleStatusDTO;
import com.ecommerce.app.service.saleStatus.SaleStatusService;

@RestController
@RequestMapping("/api/sale-status")
public class SaleStatusController {
    
    @Autowired
    private SaleStatusService saleStatusService;

    public SaleStatusController(SaleStatusService saleStatusService) {
        this.saleStatusService = saleStatusService;
    }

    @GetMapping
    public List<SaleStatusDTO> getAllSaleStatuses() {
        return saleStatusService.getAllSalesStatus();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SaleStatusDTO> getSaleStatusById(@PathVariable Long id) {
        return ResponseEntity.ok(saleStatusService.getSaleStatusById(id));
    }

    @PostMapping
    public ResponseEntity<SaleStatusDTO> createSaleStatus(@RequestBody SaleStatusDTO saleStatusDTO) {
        SaleStatusDTO newSaleStatus = saleStatusService.createSaleStatus(saleStatusDTO);
        return ResponseEntity.status(201).body(newSaleStatus);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SaleStatusDTO> updateSaleStatus(
            @PathVariable Long id, @RequestBody SaleStatusDTO saleStatusDTO) {
        return ResponseEntity.ok(saleStatusService.updateSaleStatus(id, saleStatusDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSaleStatus(@PathVariable Long id) {
        saleStatusService.deleteSaleStatus(id);
        return ResponseEntity.noContent().build();
    }

}
