package com.ecommerce.app.controller.product;

import com.ecommerce.app.dto.product.ProductUpdateDTO;
import com.ecommerce.app.dto.product.ProductDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ecommerce.app.service.product.ProductService;
import com.ecommerce.app.dto.product.ProductDetailsDTO;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public Page<ProductDTO> getAllProducts(Pageable pageable) {
        return productService.getAllProducts(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDetailsDTO> getProductById(@PathVariable int id){
        ProductDetailsDTO productDTO = productService.getProductById(id);

        return productDTO != null ? ResponseEntity.ok(productDTO) : ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/details")
    public ResponseEntity<ProductDetailsDTO> getProductDetailsById(@PathVariable int id){
        ProductDetailsDTO productDTO = productService.getProductById(id);

        return productDTO != null ? ResponseEntity.ok(productDTO) : ResponseEntity.notFound().build();
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ProductDTO>> searchProducts(@RequestParam Map<String, String> filters, Pageable pageable){
        Page<ProductDTO> products = filters.isEmpty() ? productService.getAllProducts(pageable) : productService.buildFilteredProducts(filters, pageable);
        return products != null ? ResponseEntity.ok(products) : ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/update")
    public ResponseEntity<ProductUpdateDTO> getProductUpdateById(@PathVariable int id){
        ProductUpdateDTO product = productService.productUpdateById(id);

        return product != null ? ResponseEntity.ok(product) : ResponseEntity.notFound().build();
    }

    @GetMapping("/bestsellers")
    public ResponseEntity<Page<ProductDTO>> getBestSellersProducts(@PageableDefault(size = 10)Pageable pagination) {
        Page<ProductDTO> productDTOS = productService.getBestSellers(pagination);
        if (productDTOS.isEmpty()) {
            productDTOS = productService.getAllProducts(pagination);
        }
        return productDTOS != null ? ResponseEntity.ok(productDTOS) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ProductDetailsDTO createProduct(@Valid @RequestBody ProductDetailsDTO productDTO){
        return productService.createProduct(productDTO);
    }

    @PostMapping("/import")
    public List<ProductDTO> createProducts(@Valid @RequestBody List<ProductDetailsDTO> productDTO){
        return productService.createProducts(productDTO);
    }

    @PutMapping("/update")
    public ResponseEntity<ProductDetailsDTO> updateProduct(@Valid @RequestBody ProductUpdateDTO product){
        ProductDetailsDTO updateProduct = productService.updateProduct(product);

        return updateProduct != null ? ResponseEntity.ok(updateProduct) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable int id){
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}