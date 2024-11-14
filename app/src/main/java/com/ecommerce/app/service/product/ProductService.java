package com.ecommerce.app.service.product;

import com.ecommerce.app.dto.product.ProductSpecifications;
import com.ecommerce.app.dto.product.ProductUpdateDTO;
import com.ecommerce.app.dto.product.ProductDTO;
import com.ecommerce.app.model.productImages.ProductImages;
import com.ecommerce.app.repository.product.ProductRepository;
import com.ecommerce.app.service.productImages.ImageProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.ecommerce.app.dto.product.ProductDetailsDTO;
import com.ecommerce.app.model.product.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import jakarta.validation.Valid;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ImageProductService imageProductService;

    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(product -> {
                    String cover = product.getImages().stream()
                            .filter(productImages -> Boolean.TRUE.equals(productImages.getCapaProduto()))
                            .map(ProductImages::getImagem)
                            .findFirst()
                            .orElse(null);
                    return new ProductDTO(product, cover);
                }).collect(Collectors.toList());
    }

    public ProductDetailsDTO getProductById(int id) {
        Optional<Product> product = productRepository.findById(id);
        return product.map(this::convertToDTO).orElse(null);
    }

    public ProductDTO getProductById2(int id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            return null;
        }
        Product result = product.get();
        return new ProductDTO(result, result.getImages().get(0).getImagem());
    }

    public List<ProductDTO> getProductByName(String name) {
        List<Product> product = productRepository.findByNome(name);
        if (product == null || product.isEmpty()) {
            return null;
        }
        return product.stream().map(p -> new ProductDTO(p, imageProductService.getCoverByProductId(p.getId())))
                .collect(Collectors.toList());
    }

    public List<ProductDTO> getProductByCategory(String category) {
        List<Product> product = productRepository.findByCategory(category);
        if (product == null || product.isEmpty()) {
            return null;
        }
        return product.stream().map(p -> new ProductDTO(p, imageProductService.getCoverByProductId(p.getId())))
                .collect(Collectors.toList());
    }

    public ProductDetailsDTO createProduct(@Valid ProductDetailsDTO productDTO) {
        Product product = new Product();
        product.setNome(productDTO.getName());
        product.setDescricao(productDTO.getDescription());
        product.setEstoque(productDTO.getEstoque());
        product.setCategoria(productDTO.getCategoria());
        product.setNota(productDTO.getRating());
        product.setPreco(productDTO.getPrice());
        product.setCor(productDTO.getColor());
        product.addImages(productDTO.getImages());
        product.getImages().get(0).setCapaProduto(true);
        productRepository.save(product);

        return convertToDTO(product);
    }

    public ProductDetailsDTO updateProduct(@Valid ProductUpdateDTO productDTO) {
        Optional<Product> productOptional = productRepository.findById(productDTO.id());
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            product.update(productDTO);
            productRepository.save(product);

            return convertToDTO(product);
        }
        return null;
    }

    public ProductUpdateDTO productUpdateById(int id) {
        Optional<Product> product = productRepository.findById(id);
        String cover = imageProductService.getCoverByProductId(id);
        List<String> images = imageProductService.getImagesByProductId(id);
        return product.map(value -> new ProductUpdateDTO(value, cover, images)).orElse(null);
    }

    public void deleteProduct(int id) {
        productRepository.deleteById(id);
    }

    private ProductDetailsDTO convertToDTO(Product product) {
        ProductDetailsDTO productDTO = new ProductDetailsDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getNome());
        productDTO.setDescription(product.getDescricao());
        productDTO.setCategoria(product.getCategoria());
        productDTO.setEstoque(product.getEstoque());
        productDTO.setRating(product.getNota());
        productDTO.setDiscount(product.getDiscount());
        productDTO.setPrice(product.getPreco());
        if (product.getDiscount() != null && product.getDiscount() != 0) {
            Double value = product.getPreco() - (product.getPreco() / 100) * product.getDiscount();
            productDTO.setPriceDiscount(String.valueOf(value));
        }
        productDTO.setColor(product.getCor());
        productDTO.setImages(product.getImages().stream().map(ProductImages::getImagem).toList());

        return productDTO;
    }

    public List<ProductDTO> buildFilteredProducts(Map<String, String> filters) {
        List<Product> products = getFilteredProducts(filters);

        return products.stream().map(product -> {
            String cover = product.getImages().stream()
                    .filter(productImages -> Boolean.TRUE.equals(productImages.getCapaProduto()))
                    .map(ProductImages::getImagem)
                    .findFirst()
                    .orElse(null);
            return new ProductDTO(product, cover);
        }).collect(Collectors.toList());
    }

    private List<Product> getFilteredProducts(Map<String, String> filters) {
        Specification<Product> specification = Specification.where(null);

        if (filters.containsKey("nome")) {
            specification = specification.and(ProductSpecifications.hasName(filters.get("nome")));
        }
        if (filters.containsKey("categoria")) {
            specification = specification.and(ProductSpecifications.hasCategory(filters.get("categoria")));
        }
        if (filters.containsKey("minPrice")) {
            specification = specification.and(ProductSpecifications.priceGreaterThan(Double.parseDouble(filters.get("minPrice"))));
        }
        if (filters.containsKey("maxPrice")) {
            specification = specification.and(ProductSpecifications.priceLessThan(Double.parseDouble(filters.get("maxPrice"))));
        }
        if (filters.containsKey("color")) {
            specification = specification.and(ProductSpecifications.colorEqualsTo(filters.get("color")));
        }

        return productRepository.findAll(specification);
    }

    public List<ProductDTO> createProducts(@Valid List<ProductDetailsDTO> productDTOs) {
        List<ProductDTO> simpleProduct = new ArrayList<>();
        for (ProductDetailsDTO productDTO : productDTOs) {
            Product product = new Product();
            product.setNome(productDTO.getName());
            product.setDescricao(productDTO.getDescription());
            product.setPreco(productDTO.getPrice());
            product.setNota(productDTO.getRating());
            product.setEstoque(productDTO.getEstoque());
            product.setCor(productDTO.getColor());
            product.setCategoria(productDTO.getCategoria());
            product.setDiscount(productDTO.getDiscount());
            product.setFlashSale(productDTO.getFlashSale());
            product.addImages(productDTO.getImages());
            productRepository.save(product);
            simpleProduct.add(new ProductDTO(product));
        }
        return simpleProduct;
    }
}
