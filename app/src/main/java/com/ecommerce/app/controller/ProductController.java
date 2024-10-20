package com.ecommerce.app.controller;

import com.ecommerce.app.dto.produtos.ProductUpdateDTO;
import com.ecommerce.app.dto.produtos.SimpleProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ecommerce.app.service.ProductService;
import com.ecommerce.app.dto.produtos.ProductDTO;

import java.util.Collections;
import java.util.List;

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
    public ResponseEntity<ProductDTO> getProductById(@PathVariable int id){
        ProductDTO productDTO = productService.getProductById(id);

        return productDTO != null ? ResponseEntity.ok(productDTO) : ResponseEntity.notFound().build();
    }

        // simpleProduct pega somente alguns dados do produto para a visualização no product card
        //search — Nenhum parâmetro passado.
        //search?name=John — Apenas o nome foi passado.
        //search?id=10 — Apenas o id foi passado.
        //search?name=John&id=10 — Ambos foram passados.
    @GetMapping("/search")
    public ResponseEntity<List<SimpleProductDTO>> getProduct(@RequestParam(value = "id", required = false) String id,
                                                       @RequestParam(value = "name", required = false) String name){
        if (id != null && id.length() > 0) {
            Integer key = Integer.parseInt(id);
            SimpleProductDTO simple = productService.getProductById2(key);
            return ResponseEntity.ok(List.of(simple));
        } else if (name != null && name.length() > 0) {
            List<SimpleProductDTO> simple = productService.getProductByName(name);
            return ResponseEntity.ok(simple);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ProductDTO createProduct(@RequestBody ProductDTO productDTO){
        return productService.createProduct(productDTO);
    }

    @PutMapping("/update")
    public ResponseEntity<ProductDTO> updateProduct(@RequestBody ProductUpdateDTO product){
        ProductDTO updateProduct = productService.updateProduct(product);

        return updateProduct != null ? ResponseEntity.ok(updateProduct) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable int id){
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
