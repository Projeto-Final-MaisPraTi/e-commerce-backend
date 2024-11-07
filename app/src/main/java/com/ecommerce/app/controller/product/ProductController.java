package com.ecommerce.app.controller;

import com.ecommerce.app.dto.product.ProductUpdateDTO;
import com.ecommerce.app.dto.product.ProductDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

    @Operation(summary = "Buscar todos os produtos", description = "Retorna uma lista de todos os produtos da loja")
    @GetMapping
    public Page<ProductDTO> getAllProducts(@PageableDefault(size = 10, sort = "nome") Pageable pagination){
        return productService.getAllProducts(pagination);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDetailsDTO> getProductById(@PathVariable int id){
        ProductDetailsDTO productDTO = productService.getProductById(id);

        return productDTO != null ? ResponseEntity.ok(productDTO) : ResponseEntity.notFound().build();
    }

    @GetMapping("/flashsales")
    public ResponseEntity<Page<ProductDTO>> getProductsInFlashSales(@PageableDefault(size = 10, sort = "nome") Pageable pagination){
        Page<ProductDTO> productDTO = productService.getProductsInFlashSales(pagination);

        return productDTO != null ? ResponseEntity.ok(productDTO) : ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/details")
    public ResponseEntity<ProductDetailsDTO> getProductDetailsById(@PathVariable int id){
        ProductDetailsDTO productDTO = productService.getProductById(id);

        return productDTO != null ? ResponseEntity.ok(productDTO) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Busca podendo usar nenhum ou muitos filtros simultaneamente")
    @GetMapping("/search")
    public ResponseEntity<Page<ProductDTO>> getProduct(
            @Parameter(
                    description = "Filtros de busca para os produtos\n",
                    example = "{\"nome\":\"string\",\"categoria\":\"string\",\"minPrice\":\"value\",\"maxPrice\":\"value\",\"color\":\"string\"}"
            )
            @RequestParam Map<String, String> filters,
            @PageableDefault(size = 10, sort = "nome") Pageable pagination){
        // se estiver vazio
        if (filters.isEmpty()) {
            Page<ProductDTO> productDTOS = productService.getAllProducts(pagination);
            return productDTOS != null ? ResponseEntity.ok(productDTOS) : ResponseEntity.notFound().build();
        }
        Page<ProductDTO> productDTOS = productService.buildFilteredProducts(filters, pagination);
        return productDTOS != null ? ResponseEntity.ok(productDTOS) : ResponseEntity.notFound().build();
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
