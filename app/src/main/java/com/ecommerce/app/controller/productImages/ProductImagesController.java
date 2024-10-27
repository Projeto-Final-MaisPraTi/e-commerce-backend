package com.ecommerce.app.controller.productImages;

import java.util.List;

import com.ecommerce.app.model.product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ecommerce.app.dto.productImage.ProductImagesDTO;
import com.ecommerce.app.service.productImages.ProductImagesService;

@RestController
@RequestMapping("/api/product-images")
public class ProductImagesController {
    
    @Autowired
    private ProductImagesService productImagesService;

    @GetMapping
    public List<ProductImagesDTO> getAllProductImages() {
        return productImagesService.getAllProductImages();
    }

    @GetMapping("/{id}")
    public ProductImagesDTO getImageById(@PathVariable Long id) {
        return productImagesService.getProductImagesById(id);
    }

    @PostMapping
    public ProductImagesDTO createProductImages(@RequestBody ProductImagesDTO productImagesDTO, Product product) {
        return productImagesService.createProductImages(productImagesDTO, product);
    }

    @PutMapping("/{id}")
    public ProductImagesDTO updateProductImages(@PathVariable Long id, @RequestBody ProductImagesDTO productImagesDTO, Product product) {
        return productImagesService.updateProductImages(id, productImagesDTO, product);
    }

    @DeleteMapping("/{id}")
    public void deleteImage(@PathVariable Long id) {
        productImagesService.deleteProductImages(id);
    }

}
