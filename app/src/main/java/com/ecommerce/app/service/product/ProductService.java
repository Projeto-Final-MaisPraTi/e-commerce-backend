package com.ecommerce.app.service;

import com.ecommerce.app.dto.product.ProductSpecifications;
import com.ecommerce.app.dto.product.ProductUpdateDTO;
import com.ecommerce.app.dto.product.ProductDTO;
import com.ecommerce.app.model.productImages.ProductImages;
import com.ecommerce.app.repository.product.ProductRepository;
import com.ecommerce.app.service.productImages.ImageProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.ecommerce.app.dto.product.ProductDetailsDTO;
import com.ecommerce.app.model.product.Product;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired // faz injeção de dependência automática
    private ProductRepository productRepository;

    @Autowired
    private ImageProductService imageProductService;

    public Page<ProductDTO> getAllProducts(Pageable pagination){
        // retorna a lista de produtos convertidos e coletados
        return productRepository
                .findAll(pagination) // pega todos os produto do bd
                .map(product -> {
                            String cover = product.getImages().stream()
                                    .filter(productImages -> Boolean.TRUE.equals(productImages.getCapaProduto()))
                                    .map(ProductImages::getImagem)
                                    .findFirst()
                                    .orElse(null);
                            return new ProductDTO(product, cover);
                        });
    }

    public ProductDetailsDTO getProductById(int id){
        Optional<Product> product = productRepository.findById(id);
        return product.map(this::convertToDTO).orElse(null);
    }

    public ProductDTO getProductById2(int id){
        Optional<Product> product = productRepository.findById(id);
        if (!product.isPresent()) {
            return null;
        }
        Product result = product.get();
        return new ProductDTO(result, result.getImages().get(0).getImagem());
    }

    public List<ProductDTO> getProductByName(String name){
        List<Product> product = productRepository.findByNome(name);
        if (product == null || product.size() == 0) {
            return null;
        }
        return product.stream().map(p -> new ProductDTO(p, imageProductService.getCoverByProductId(p.getId()))).
                collect(Collectors.toList());
    }

    public List<ProductDTO> getProductByCategory(String category) {
        List<Product> product = productRepository.findByCategory(category);
        if (product == null || product.size() == 0) {
            return null;
        }
        return product.stream().map(p -> new ProductDTO(p, imageProductService.getCoverByProductId(p.getId()))).
                collect(Collectors.toList());
    }

    public ProductDetailsDTO createProduct(ProductDetailsDTO productDTO){
        Product product = new Product();
        product.setNome(productDTO.getName());
        product.setDescricao(productDTO.getDescription());
        product.setEstoque(productDTO.getStock());
        product.setCategoria(productDTO.getCategory());
        product.setNota(productDTO.getRating());
        product.setPreco(Double.parseDouble(productDTO.getPrice()));
        product.setCor(productDTO.getColor());
        product.setDiscount(0);
        product.setFlashSale(false);
        product.addImages(productDTO.getImages());
        product.getImages().get(0).setCapaProduto(true);
        productRepository.save(product);

        return convertToDTO(product);
    }



    public ProductDetailsDTO updateProduct(ProductUpdateDTO productDTO){
        Optional<Product> productOptional = productRepository.findById(productDTO.id());
        if(productOptional.isPresent()){
            Product product = productOptional.get();
            product.update(productDTO);
            productRepository.save(product);

            return convertToDTO(product);
        }

        return null;
    }

    public ProductUpdateDTO productUpdateById(int id){
        Optional<Product> product = productRepository.findById(id);
        String cover = imageProductService.getCoverByProductId(id);

        List<String> images = imageProductService.getImagesByProductId(id);
        ProductUpdateDTO updateDTO = new ProductUpdateDTO(product.get(), cover, images);
        return updateDTO;
    }

    public void deleteProduct(int id){
        productRepository.deleteById(id);
    }

    private ProductDetailsDTO convertToDTO(Product product){
        ProductDetailsDTO productDTO = new ProductDetailsDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getNome());
        productDTO.setDescription(product.getDescricao());
        productDTO.setCategory(product.getCategoria());
        productDTO.setStock(product.getEstoque());
        productDTO.setRating(product.getNota());
        productDTO.setDiscount(product.getDiscount());
        productDTO.setPrice(formatValue(product.getPreco()));
        if (product.getDiscount() != 0) {
            productDTO.setPriceDiscount(calculateDiscount(product.getPreco(), product.getDiscount()));
        }
        productDTO.setColor(product.getCor());
        productDTO.setImages(product.getImages().stream().map(image -> image.getImagem()).toList());

        return productDTO;
    }

    public String formatValue(Double price) {
        NumberFormat currency = NumberFormat.getCurrencyInstance();
        return currency.format(price);
    }

    public String calculateDiscount(Double price, Integer discount) {
        if (discount == 0) {
            return null;
        }
        Double value = price - (price / 100) * discount;
        return (formatValue(value));
    }

    public Page<ProductDTO> buildFilteredProducts(Map<String, String> filters, Pageable pagination) {
        // sera que volta nulo ?
        Page<Product> products = getFilteredProducts(filters, pagination);

        return products.map(product -> {
            String cover = product.getImages().stream()
                    .filter(productImages -> Boolean.TRUE.equals(productImages.getCapaProduto()))
                    .map(ProductImages::getImagem)
                    .findFirst()
                    .orElse(null);

            return new ProductDTO(product, cover);
        });
    }

    private Page<Product> getFilteredProducts(Map<String, String> filters, Pageable pagination) {
        Specification<Product> specification = Specification.where(null);

        if (filters.containsKey("nome")) {
            specification = specification.and(ProductSpecifications
                    .hasName(filters.get("nome")));
        }
            if (filters.containsKey("categoria")) {
            specification = specification.and(ProductSpecifications
                    .hasCategory(filters.get("categoria")));
        }
        if (filters.containsKey("minPrice")) {
            specification = specification.and(ProductSpecifications
                    .priceGreaterThan(Double.parseDouble(filters.get("minPrice"))));
        }
        if (filters.containsKey("maxPrice")) {
            specification = specification.and(ProductSpecifications
                    .priceLessThan(Double.parseDouble(filters.get("maxPrice"))));
        }
        if (filters.containsKey("color")) {
            specification = specification.and(ProductSpecifications.
                    colorEqualsTo(filters.get("color")));
        }

        return productRepository.findAll(specification, pagination);
    }

    public List<ProductDTO> createProducts(List<ProductDetailsDTO> productDTOs) {
        List<ProductDTO> simpleProduct = new ArrayList<>();
        for (ProductDetailsDTO productDTO : productDTOs) {
            Product product = new Product();
            product.setNome(productDTO.getName());
            product.setDescricao(productDTO.getDescription());
            product.setPreco(Double.parseDouble(productDTO.getPrice()));
            product.setNota(productDTO.getRating());
            product.setEstoque(productDTO.getStock());
            product.setCor(productDTO.getColor());
            product.setCategoria(productDTO.getCategory());
            product.setDiscount(productDTO.getDiscount());
            product.setFlashSale(productDTO.getFlashSale());
            ProductDTO simple = new ProductDTO(product);
            simpleProduct.add(simple);
            productRepository.save(product);
        }
        return simpleProduct;
    }

    public Page<ProductDTO> getProductsInFlashSales(Pageable pagination) {
        Page<Product> produts = productRepository.getProductsInFlashSales(pagination);

        Page<ProductDTO> productDTOS = produts.map(productDTO -> {
                String cover = imageProductService.getCoverByProductId(productDTO.getId());
                return new ProductDTO(productDTO, cover);
        });

        return productDTOS;
    }
}
