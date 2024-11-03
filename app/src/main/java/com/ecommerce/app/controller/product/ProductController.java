package com.ecommerce.app.controller;

import com.ecommerce.app.dto.product.ProductUpdateDTO;
import com.ecommerce.app.dto.product.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ecommerce.app.service.ProductService;
import com.ecommerce.app.dto.product.ProductDetailsDTO;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public List<ProductDTO> getAllProducts(){
        return productService.getAllProducts();
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

        // simpleProduct pega somente alguns dados do produto para a visualização no product card
        //search — Nenhum parâmetro passado.
        //search?name=John — Apenas o nome foi passado.
        //search?id=10 — Apenas o id foi passado.
        //search?name=John&id=10 — Ambos foram passados.
    @GetMapping("/busca")
    public ResponseEntity<List<ProductDTO>> getProduct(@RequestParam Map<String, String> filters){
        // se estiver vazio
        if (filters.isEmpty()) {
            List<ProductDTO> productDTOS = productService.getAllProducts();
            return productDTOS != null ? ResponseEntity.ok(productDTOS) : ResponseEntity.notFound().build();
        }
        if (!filters.isEmpty()) {
            List<ProductDTO> productDTOS = productService.buildFilteredProducts(filters);
            return productDTOS != null ? ResponseEntity.ok(productDTOS) : ResponseEntity.notFound().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ProductDetailsDTO createProduct(@RequestBody ProductDetailsDTO productDTO){
        return productService.createProduct(productDTO);
    }

    @PostMapping("/import")
    public List<ProductDTO> createProducts(@RequestBody List<ProductDetailsDTO> productDTO){
        List<ProductDTO> productDTOS =  productService.createProducts(productDTO);
        return productDTOS;
    }

    @PutMapping("/update")
    public ResponseEntity<ProductDetailsDTO> updateProduct(@RequestBody ProductUpdateDTO product){
        ProductDetailsDTO updateProduct = productService.updateProduct(product);

        return updateProduct != null ? ResponseEntity.ok(updateProduct) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable int id){
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/update")
    public ResponseEntity<ProductUpdateDTO> getProductUpdateById(@PathVariable int id){
        ProductUpdateDTO product = productService.productUpdateById(id);

        return product != null ? ResponseEntity.ok(product) : ResponseEntity.notFound().build();
    }
}
