package com.ecommerce.app.controller.itemCart;

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

import com.ecommerce.app.dto.itemCart.ItemCartDTO;
import com.ecommerce.app.service.itemCart.ItemCartService;

@RestController
@RequestMapping("/api/cart-items")
public class ItemCartController {

    @Autowired
    private ItemCartService itemCartService;

    @GetMapping
    public List<ItemCartDTO> getAllCartItems() {
        return itemCartService.getAllCartItems();
    }

    @GetMapping("/{id}")
    public ItemCartDTO getCartItemById(@PathVariable Integer id) {
        return itemCartService.getCartItemById(id);
    }

    @PostMapping
    public ItemCartDTO addItemToCart(@RequestBody ItemCartDTO itemCartDTO) {
        return itemCartService.addItemToCart(itemCartDTO);
    }

    @PutMapping("/{id}")
    public ItemCartDTO updateItemCart(@PathVariable Integer id, @RequestBody ItemCartDTO itemCartDTO) {
        return itemCartService.updateItemCart(id, itemCartDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteItemCart(@PathVariable Integer id) {
        itemCartService.deleteItemCart(id);
    }

}
