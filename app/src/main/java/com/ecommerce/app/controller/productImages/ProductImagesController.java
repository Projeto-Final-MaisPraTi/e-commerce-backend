package com.ecommerce.app.controller.productImages;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.app.dto.productImage.ProductImagesDTO;
import com.ecommerce.app.service.productImages.ProductImagesService;

@RestController
@RequestMapping("/api/product-images")
public class ProductImagesController {
    
    @Autowired
    private ProductImagesService productImagesService;

    @GetMapping
    public List<ProductImagesDTO> getAllImages() {
        return productImagesService.getAllImages();
    }

    @GetMapping("/{id}")
    public ProductImagesDTO getImageById(@PathVariable Long id) {
        return productImagesService.getImageById(id);
    }

    @PostMapping
    public ProductImagesDTO createImage(@RequestBody ProductImagesDTO productImagesDTO) {
        return productImagesService.createImage(productImagesDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteImage(@PathVariable Long id) {
        productImagesService.deleteImage(id);
    }

}
