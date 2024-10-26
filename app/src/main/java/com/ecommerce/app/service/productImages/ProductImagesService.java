package com.ecommerce.app.service.productImages;

import com.ecommerce.app.dto.product.ProductDTO;
import com.ecommerce.app.dto.productImage.ProductImagesDTO;
import com.ecommerce.app.model.product.Product;
import com.ecommerce.app.model.productImages.ProductImages;
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

    public List<ProductImagesDTO> getAllProductImages() {
        return productImagesRepository
                .findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ProductImagesDTO getProductImagesById(Long id) {
        Optional<ProductImages> payment = ProductImagesRepository.findById(id);

        return payment.map(this::convertToDTO).orElseThrow(() -> new RuntimeException("Imagem do produto não encontrada!"));
    }

    public ProductImagesDTO createProductImages(ProductImagesDTO productImagesDTO) {
        ProductImages productImages = new ProductImages();
        productImages.setImagem(productImagesDTO.getImagem());
        productImages.setProduct(productImagesDTO.getId_produto());

        paymentRepository.save(payment);

        return convertToDTO(payment);
    }

    public PaymentDTO updateProductImages(Long id, PaymentDTO paymentDTO) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tipo de pagamento não encontrado!"));

        payment.setTipo(paymentDTO.getTipo());
        paymentRepository.save(payment);

        return convertToDTO(payment);
    }

    public void deleteProductImages(Long id) {
        paymentRepository.deleteById(id);
    }

    private PaymentDTO convertToDTO(Payment payment) {
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setId(payment.getId());
        paymentDTO.setTipo(payment.getTipo());

        return paymentDTO;
    }

    private ProductImagesDTO convertToProductImagesDTO(ProductImages productImages) {
        return ProductImagesDTO.builder()
                .id(productImages.getId())
                .imagem(productImages.getImagem())
                .id_produto(productImages.getId())
                .build();
    }


    private Product convertToProductImagesEntity(ProductDTO productDTO) {
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setNome(productDTO.getNome());
        product.setPreco(productDTO.getPreco());
        product.setCategoria(productDTO.getCategoria());
        product.setNota(productDTO.getNota());
        product.setCor(productDTO.getCor());
        product.setEstoque(Integer.parseInt(productDTO.getEstoque()));  // Converter para int
        return product;
    }

}
