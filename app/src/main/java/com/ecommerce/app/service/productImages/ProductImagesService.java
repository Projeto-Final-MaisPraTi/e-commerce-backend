package com.ecommerce.app.service.productImages;

import com.ecommerce.app.dto.productImage.ProductImagesDTO;
import com.ecommerce.app.model.product.Product;
import com.ecommerce.app.model.productImages.ProductImages;
import com.ecommerce.app.repository.product.ProductRepository;
import com.ecommerce.app.repository.productImages.ProductImagesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductImagesService {

    private final ProductImagesRepository productImagesRepository;
    private final ProductRepository productRepository;

    public List<ProductImagesDTO> getAllProductImages() {
        return productImagesRepository
                .findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ProductImagesDTO getProductImagesById(Integer id) {
        Optional<ProductImages> productImages= productImagesRepository.findById(id);

        return productImages.map(this::convertToDTO).orElseThrow(() -> new RuntimeException("Imagem do produto não encontrada!"));
    }

    public ProductImagesDTO createProductImages(ProductImagesDTO productImagesDTO, Product product) {
        Product prod = productRepository.findById(productImagesDTO.getId_produto())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        ProductImages productImages = new ProductImages();
        productImages.setImagem(productImagesDTO.getImagem());

        // Associa a entidade Product diretamente
        productImages.setProduct(product);

        productImagesRepository.save(productImages);

        return convertToDTO(productImages);
    }

    public ProductImagesDTO updateProductImages(Integer id, ProductImagesDTO productImagesDTO, Product product) {
        ProductImages productImages = productImagesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Imagem do produto não encontrada!"));

        productImages.setImagem(productImagesDTO.getImagem());
        productImages.setProduct(product);
        productImagesRepository.save(productImages);

        return convertToDTO(productImages);
    }

    public void deleteProductImages(Integer id) {
        productImagesRepository.deleteById(id);
    }

    private ProductImagesDTO convertToDTO(ProductImages productImages) {
        return ProductImagesDTO.builder()
                .id(productImages.getId())
                .imagem(productImages.getImagem())
                .id_produto(productImages.getProduct() != null ? productImages.getProduct().getId() : null) // Pegando o ID do produto
                .build();
    }

}
