package com.ecommerce.app.controller.productImages;

import java.util.List;

import com.ecommerce.app.model.product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ecommerce.app.dto.productImage.ProductImagesDTO;
import com.ecommerce.app.service.productImages.ProductImagesService;

@CrossOrigin(origins = "http://localhost:5173")
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
    public ProductImagesDTO getImageById(@PathVariable Integer id) {
        return productImagesService.getProductImagesById(id);
    }

    @PostMapping
    public ProductImagesDTO createProductImages(@RequestBody ProductImagesDTO productImagesDTO, Product product) {
        return productImagesService.createProductImages(productImagesDTO, product);
    }

    @PutMapping("/{id}")
    public ProductImagesDTO updateProductImages(@PathVariable Integer id, @RequestBody ProductImagesDTO productImagesDTO, Product product) {
        return productImagesService.updateProductImages(id, productImagesDTO, product);
    }

    @DeleteMapping("/{id}")
    public void deleteImage(@PathVariable Integer id) {
        productImagesService.deleteProductImages(id);
    }

}
