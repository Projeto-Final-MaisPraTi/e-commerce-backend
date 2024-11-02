package com.ecommerce.app.service;

import com.ecommerce.app.dto.produtos.ProductSpecifications;
import com.ecommerce.app.dto.produtos.ProductUpdateDTO;
import com.ecommerce.app.dto.produtos.SimpleProductDTO;
import com.ecommerce.app.model.ImageProduct;
import com.ecommerce.app.repository.ImageRepository;
import com.ecommerce.app.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.ecommerce.app.dto.produtos.ProductDTO;
import com.ecommerce.app.model.Product;

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

    public List<SimpleProductDTO> getAllProducts(){
        // retorna a lista de produtos convertidos e coletados
        return productRepository
                .findAll() // pega todos os produto do bd
                .stream()
                .map(product -> {
                            String cover = product.getImages().stream()
                                    .filter(imageProduct -> Boolean.TRUE.equals(imageProduct.getCapaProduto()))
                                    .map(ImageProduct::getImagem)
                                    .findFirst()
                                    .orElse(null);
                            return new SimpleProductDTO(product, cover);
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

    public ProductDTO getProductById(int id){
        Optional<Product> product = productRepository.findById(id);
        return product.map(this::convertToDTO).orElse(null);
    }

    public SimpleProductDTO getProductById2(int id){
        Optional<Product> product = productRepository.findById(id);
        if (!product.isPresent()) {
            return null;
        }
        Product result = product.get();
        return new SimpleProductDTO(result, result.getImages().get(0).getImagem());
    }

    public List<SimpleProductDTO> getProductByName(String name){
        List<Product> product = productRepository.findByNome(name);
        if (product == null || product.size() == 0) {
            return null;
        }
        return product.stream().map(p -> new SimpleProductDTO(p, imageProductService.getCoverByProductId(p.getId()))).
                collect(Collectors.toList());
    }

    public List<SimpleProductDTO> getProductByCategory(String category) {
        List<Product> product = productRepository.findByCategory(category);
        if (product == null || product.size() == 0) {
            return null;
        }
        return product.stream().map(p -> new SimpleProductDTO(p, imageProductService.getCoverByProductId(p.getId()))).
                collect(Collectors.toList());
    }

    public ProductDTO createProduct(ProductDTO productDTO){
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



    public ProductDTO updateProduct(ProductUpdateDTO productDTO){
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

    public List<SimpleProductDTO> buildFilteredProducts(Map<String, String> filters) {
        // sera que volta nulo ?
        List<Product> products = getFilteredProducts(filters);

        return products.stream().map(product -> {
            String cover = product.getImages().stream()
                    .filter(imageProduct -> Boolean.TRUE.equals(imageProduct.getCapaProduto()))
                    .map(ImageProduct::getImagem)
                    .findFirst()
                    .orElse(null);

            return new SimpleProductDTO(product, cover);
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
