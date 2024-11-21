package com.ecommerce.app.controller.salesItems;

import java.util.List;

import com.ecommerce.app.dto.itemCart.ItemCartDetailsDTO;
import com.ecommerce.app.dto.sales.SaleDTO;
import com.ecommerce.app.model.product.Product;
import com.ecommerce.app.model.sales.Sales;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ecommerce.app.dto.salesItems.SalesItemsDTO;
import com.ecommerce.app.service.salesItems.SalesItemsService;
import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:5173")
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
    public ResponseEntity<SalesItemsDTO> getSalesItemById(@PathVariable Integer id) {
        return ResponseEntity.ok(salesItemsService.getSalesItemById(id));
    }

    @PostMapping
    public ResponseEntity<List<SalesItemsDTO>> createSalesItem(@Valid @RequestBody SaleDTO saleDTO) {
        List<SalesItemsDTO> salesItemsDTO = salesItemsService.createSalesItem(saleDTO);
        salesItemsService.deleteItensInCart(saleDTO.getItemsCart());
        return ResponseEntity.ok(salesItemsDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SalesItemsDTO> updateSalesItem(
            @PathVariable Integer id, @Valid @RequestBody SalesItemsDTO salesItemsDTO, Sales sales, Product product) {
        return ResponseEntity.ok(salesItemsService.updateSalesItem(id, salesItemsDTO, sales, product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSalesItem(@PathVariable Integer id) {
        salesItemsService.deleteSalesItem(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<SalesItemsDTO>> getUserPurchaseHistory(@PathVariable Integer userId) {
        List<SalesItemsDTO> userPurchaseHistory = salesItemsService.getUserPurchaseHistory(userId);
        return ResponseEntity.ok(userPurchaseHistory);
    }
}