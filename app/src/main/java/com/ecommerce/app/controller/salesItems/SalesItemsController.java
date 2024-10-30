package com.ecommerce.app.controller.salesItems;

import java.util.List;

import com.ecommerce.app.model.product.Product;
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

import com.ecommerce.app.dto.salesItems.SalesItemsDTO;
import com.ecommerce.app.service.salesItems.SalesItemsService;

@RestController
@RequestMapping("/api/sales-items")
public class SalesItemsController {
    
    @Autowired
    private SalesItemsService salesItemsService;

    public SalesItemsController(SalesItemsService salesItemsService) {
        this.salesItemsService = salesItemsService;
    }

    @GetMapping
    public List<SalesItemsDTO> getAllSalesItems() {
        return salesItemsService.getAllSalesItems();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalesItemsDTO> getSalesItemById(@PathVariable Long id) {
        return ResponseEntity.ok(salesItemsService.getSalesItemById(id));
    }

    @PostMapping
    public ResponseEntity<SalesItemsDTO> createSalesItem(@RequestBody SalesItemsDTO salesItemsDTO, Product product) {
        return ResponseEntity.ok(salesItemsService.createSalesItem(salesItemsDTO, product));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SalesItemsDTO> updateSalesItem(
            @PathVariable Long id, @RequestBody SalesItemsDTO salesItemsDTO, Product product) {
        return ResponseEntity.ok(salesItemsService.updateSalesItem(id, salesItemsDTO, product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSalesItem(@PathVariable Long id) {
        salesItemsService.deleteSalesItem(id);
        return ResponseEntity.noContent().build();
    }

}
