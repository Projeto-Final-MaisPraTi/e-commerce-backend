package com.ecommerce.app.service;

import com.ecommerce.app.dto.produtos.ProductUpdateDTO;
import com.ecommerce.app.dto.produtos.SimpleProductDTO;
import com.ecommerce.app.exception.BadRequestException;
import com.ecommerce.app.exception.DatabaseOperationException;
import com.ecommerce.app.repository.ImageRepository;
import com.ecommerce.app.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ecommerce.app.dto.produtos.ProductDTO;
import com.ecommerce.app.model.Product;
import com.ecommerce.app.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired // faz injeção de dependência automática
    private ProductRepository productRepository;

    @Autowired
    private ImageProductService imageProductService;

    public List<ProductDTO> getAllProducts(){
        try{
        // retorna a lista de produtos convertidos e coletados
        return productRepository
                .findAll() // pega todos os produto do bd
                .stream() // os produtos são colocados em uma stream
                .map(this::convertToDTO) // cada produto é convertido e abstraído apenas os dados que compõe o DTO
                .collect(Collectors.toList()); // coleta os dados convertidos e transforma em uma lista
        } catch (Exception e) {
            throw new DatabaseOperationException("Error retrieving products");
        }
    }

    public ProductDTO getProductById(int id){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found 1"));
        return convertToDTO(product);
    }

    public SimpleProductDTO getProductById2(int id){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        return new SimpleProductDTO(product);
    }

    public List<SimpleProductDTO> getProductByName(String name){
        List<Product> products = productRepository.findByNome(name);
        if (products == null || products.isEmpty()) {
            throw new ResourceNotFoundException("No products found with name: " + name);
        }
        return products.stream().map(p -> new SimpleProductDTO(p)).collect(Collectors.toList());
    }

    public ProductDTO createProduct(ProductDTO productDTO){
        if (productDTO.getNome() == null || productDTO.getNome().isEmpty()) {
            throw new BadRequestException("Product name cannot be null or empty");
        }
        if (productDTO.getPreco() == null || productDTO.getPreco().isEmpty() || Double.parseDouble(productDTO.getPreco()) <= 0) {
            throw new BadRequestException("Product price must be a positive value");
        }
        if (productDTO.getEstoque() == null || productDTO.getEstoque() <= 0) {
            throw new BadRequestException("Product stock must be greater than 0");
        }
        if (productDTO.getImages() == null || productDTO.getImages().isEmpty()) {
            throw new BadRequestException("Product must have at least one image");
        }
        if (productDTO.getCategoria() == null || productDTO.getCategoria().isEmpty()) {
            throw new BadRequestException("Product category cannot be null or empty");
        }
        try {
        Product product = new Product();
        product.setNome(productDTO.getNome());
        product.setDescricao(productDTO.getDescricao());
        product.setEstoque(productDTO.getEstoque());
        product.setCategoria(productDTO.getCategoria());
        product.setNota(productDTO.getNota());
        product.setPreco(Double.parseDouble(productDTO.getPreco()));
        product.setCor(productDTO.getCor());
        product.addImages(productDTO.getImages());
        product.getImages().get(0).setCapaProduto(true);
        productRepository.save(product);

        return convertToDTO(product);

        } catch (Exception e) {
            throw new DatabaseOperationException("Error saving product");
        }
    }

    public ProductDTO updateProduct(ProductUpdateDTO productDTO){
        Product product = productRepository.findById(productDTO.id())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        try {
        product.update(productDTO);
        productRepository.save(product);
        return convertToDTO(product);
        } catch (Exception e) {
            throw new DatabaseOperationException("Error updating product");
        }
    }

    public ProductUpdateDTO getProductUpdateById(int id){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        String cover = imageProductService.getCoverByProductId(id);
        List<String> images = imageProductService.getImagesByProductId(id);
        return new ProductUpdateDTO(product, cover, images);
    }

    public void deleteProduct(int id){
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product not found");
        }
        try{
        productRepository.deleteById(id);
        } catch (Exception e) {
            throw new DatabaseOperationException("Error deleting product");
        }
    }

    private ProductDTO convertToDTO(Product product){
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setNome(product.getNome());
        productDTO.setDescricao(product.getDescricao());
        productDTO.setCategoria(product.getCategoria());
        productDTO.setEstoque(product.getEstoque());
        productDTO.setNota(product.getNota());
        productDTO.setPreco(product.getPreco().toString());
        productDTO.setCor(product.getCor());
        productDTO.setImages(product.getImages().stream().map(image -> image.getImagem()).toList());

        return productDTO;
    }

}
