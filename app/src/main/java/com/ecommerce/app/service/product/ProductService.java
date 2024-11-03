package com.ecommerce.app.service;

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

    public List<ProductDTO> getAllProducts(){
        // retorna a lista de produtos convertidos e coletados
        return productRepository
                .findAll() // pega todos os produto do bd
                .stream()
                .map(product -> {
                            String cover = product.getImages().stream()
                                    .filter(productImages -> Boolean.TRUE.equals(productImages.getCapaProduto()))
                                    .map(ProductImages::getImagem)
                                    .findFirst()
                                    .orElse(null);
                            return new ProductDTO(product, cover);
                        }).collect(Collectors.toList());
//                .stream() // os produtos são colocados em uma stream
//                .map(product -> new SimpleProductDTO(product, product.getImages()
//                        .stream()
//                        .filter(image -> Boolean.TRUE.equals(image.getCapaProduto()))
//                        .map(ImageProduct::getImagem)
//                        .findFirst()
//                        .orElse(null))) // cada produto é convertido e abstraído apenas os dados que compõe o DTO
//                .collect(Collectors.toList()); // coleta os dados convertidos e transforma em uma lista
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

    public List<ProductDTO> buildFilteredProducts(Map<String, String> filters) {
        // sera que volta nulo ?
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

        return productRepository.findAll(specification);
    }

}
