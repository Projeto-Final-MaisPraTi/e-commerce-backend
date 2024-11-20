package com.ecommerce.app.controller.itemCart;

import java.util.List;
import java.util.Map;

import com.ecommerce.app.dto.itemCart.ItemCartDetailsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ecommerce.app.dto.itemCart.ItemCartDTO;
import com.ecommerce.app.service.itemCart.ItemCartService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/itemcart")
@CrossOrigin(origins = "http://localhost:5173")
public class ItemCartController {

    @Autowired
    private ItemCartService itemCartService;

    @GetMapping
    public List<ItemCartDetailsDTO> getAllCartItemsByUser() {
        return itemCartService.getAllCartItemsByUser();
    }

    @GetMapping("/{id}")
    public ItemCartDTO getCartItemById(@PathVariable Integer id) {
        return itemCartService.getCartItemById(id);
    }

    @PostMapping
    public ItemCartDTO addItemToCart(@Valid @RequestBody ItemCartDTO itemCartDTO) {
        return itemCartService.addItemToCart(itemCartDTO);
    }

    @PutMapping("/{id}")
    public ItemCartDTO updateItemCart(@PathVariable Integer id, @Valid @RequestBody ItemCartDTO itemCartDTO) {
        return itemCartService.updateItemCart(id, itemCartDTO);
    }

    @PatchMapping("/{id}/quantity")
    public ResponseEntity<ItemCartDetailsDTO> updateQuantity(@PathVariable Integer id, @RequestBody Map<String, Integer> requestBody) {
        Integer quantity = requestBody.get("quantity");
        if (quantity == null) {
            throw new IllegalArgumentException("Quantity is missing in the request body");
        }
        ItemCartDetailsDTO updatedItem = itemCartService.updateQuantity(id, quantity);
        return ResponseEntity.ok(updatedItem);
    }

    @DeleteMapping("/{id}")
    public void deleteItemCart(@PathVariable Integer id) {
        itemCartService.deleteItemCart(id);
    }
}