package com.ecommerce.app.controller.product;

import com.ecommerce.app.dto.product.ProductUpdateDTO;
import com.ecommerce.app.dto.product.ProductDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.ecommerce.app.service.product.ProductService;
import com.ecommerce.app.dto.product.ProductDetailsDTO;

import java.net.URI;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Operation(
            summary = "Buscar todos os produtos",
            description = "Retorna uma lista paginada de todos os produtos da loja.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de produtos retornada com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Nenhum produto encontrado")
            }
    )
    @GetMapping
    public Page<ProductDTO> getAllProducts(@PageableDefault(size = 10, sort = "nome") Pageable pagination){
        return productService.getAllProducts(pagination);
    }

    @Operation(
            summary = "Buscar produto por ID",
            description = "Retorna os detalhes de um produto específico pelo ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Produto encontrado"),
                    @ApiResponse(responseCode = "404", description = "Produto não encontrado")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<ProductDetailsDTO> getProductById(@PathVariable int id){
        ProductDetailsDTO productDTO = productService.getProductById(id);

        return productDTO != null ? ResponseEntity.ok(productDTO) : ResponseEntity.notFound().build();
    }

    @Operation(
            summary = "Buscar produtos em oferta relâmpago",
            description = "Retorna uma lista paginada de produtos em flash sales."
    )
    @GetMapping("/flashsales")
    public ResponseEntity<Page<ProductDTO>> getProductsInFlashSales(@PageableDefault(size = 10, sort = "nome") Pageable pagination){
        Page<ProductDTO> productDTO = productService.getProductsInFlashSales(pagination);

        return productDTO != null ? ResponseEntity.ok(productDTO) : ResponseEntity.notFound().build();
    }

    @Operation(
            summary = "Buscar detalhes do produto por ID",
            description = "Retorna os detalhes de um produto específico com base no ID fornecido, contendo todas as informações do produto.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Detalhes do produto retornados com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Produto não encontrado")
            }
    )
    @GetMapping("/{id}/details")
    public ResponseEntity<ProductDetailsDTO> getProductDetailsById(@PathVariable int id){
        ProductDetailsDTO productDTO = productService.getProductById(id);

        return productDTO != null ? ResponseEntity.ok(productDTO) : ResponseEntity.notFound().build();
    }

    @Operation(
            summary = "Buscar produtos por filtros",
            description = "Permite buscar produtos aplicando múltiplos filtros.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Produtos encontrados"),
                    @ApiResponse(responseCode = "404", description = "Nenhum produto encontrado com os filtros aplicados")
            }
    )
    @GetMapping("/search")
    public ResponseEntity<Page<ProductDTO>> getProduct(
            @Parameter(
                    description = "Filtros de busca como nome, categoria, preço mínimo/máximo e cor.\n",
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

    @Operation(
            summary = "Buscar informações de produto para atualização",
            description = "Retorna os dados do produto com base no ID para preencher os campos de atualização no frontend. Cada campo de input será preenchido com o valor correspondente do produto.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Dados do produto retornados para atualização com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Produto não encontrado")
            }
    )
    @GetMapping("/{id}/update")
    public ResponseEntity<ProductUpdateDTO> getProductUpdateById(@PathVariable int id){
        ProductUpdateDTO product = productService.productUpdateById(id);

        return product != null ? ResponseEntity.ok(product) : ResponseEntity.notFound().build();
    }

    @Operation(
            summary = "Buscar os produtos mais vendidos",
            description = "Retorna uma lista paginada dos produtos mais vendidos da loja. Caso não haja produtos mais vendidos, retorna todos os produtos.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Produtos mais vendidos retornados com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Nenhum produto encontrado(Provavel que não aconteça)")
            }
    )
    @GetMapping("/bestsellers")
    public ResponseEntity<Page<ProductDTO>> getBestSellersProducts(@PageableDefault(size = 10)Pageable pagination) {
        Page<ProductDTO> productDTOS = productService.getBestSellers(pagination);
        if (productDTOS.isEmpty()) {
            productDTOS = productService.getAllProducts(pagination);
        }
        return productDTOS != null ? ResponseEntity.ok(productDTOS) : ResponseEntity.notFound().build();
    }

    @Operation(
            summary = "Criar um novo produto",
            description = "Adiciona um novo produto ao banco de dados.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Produto criado com sucesso")
            }
    )
    @PostMapping
    public ResponseEntity<ProductDetailsDTO> createProduct(@Valid @RequestBody ProductDetailsDTO productDTO){
        ProductDetailsDTO product = productService.createProduct(productDTO);
        URI location =  URI.create("/api/product/" + product.getId());
        return ResponseEntity.created(location).body(product);
    }

    @Operation(
            summary = "Importar múltiplos produtos",
            description = "Permite a criação de vários produtos de uma vez através de uma lista de detalhes de produto.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Produtos criados com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Erro na validação dos dados fornecidos"),
                    @ApiResponse(responseCode = "500", description = "Erro interno ao criar os produtos")
            }
    )
    @PostMapping("/import")
    public List<ProductDTO> createProducts(@Valid @RequestBody List<ProductDetailsDTO> productDTO){
        List<ProductDTO> productDTOS =  productService.createProducts(productDTO);
        return productDTOS;
    }

    @Operation(
            summary = "Atualizar um produto",
            description = "Atualiza os detalhes de um produto existente.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Produto não encontrado")
            }
    )
    @PutMapping("/update")
    public ResponseEntity<ProductDetailsDTO> updateProduct(@Valid @RequestBody ProductUpdateDTO product){
        ProductDetailsDTO updateProduct = productService.updateProduct(product);

        return updateProduct != null ? ResponseEntity.ok(updateProduct) : ResponseEntity.notFound().build();
    }

    @Operation(
            summary = "Deletar produto por ID",
            description = "Remove um produto pelo ID.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Produto deletado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Produto não encontrado")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable int id){
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}