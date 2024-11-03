package com.ecommerce.app.service.productImages;

import com.ecommerce.app.model.productImages.ProductImages;
import com.ecommerce.app.model.product.Product;
import com.ecommerce.app.repository.productImages.ProductImagesRepository;
import com.ecommerce.app.repository.product.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ImageProductService {

    @Autowired
    private ProductImagesRepository productImagesRepository;

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public void deleteImageByUrl(String imageUrl) {
        Optional<ProductImages> imagem = productImagesRepository.findByImagem(imageUrl);
        if (imagem.isPresent()) {
            Product product = imagem.get().getProduct();
            product.getImages().remove(imagem.get());
            productImagesRepository.delete(imagem.get());
        } else {
            throw new EntityNotFoundException("Erro ao buscar imagem " + imageUrl);
        }
    }

    @Transactional
    public void addImageCover(Integer id, String imageUrl) {
        Optional <Product> optionalProduct = productRepository.findById(id);

        if (optionalProduct.isPresent()) {

            Product product = optionalProduct.get();
            ProductImages image = new ProductImages();

            image.setImagem(imageUrl);
            image.setProduct(product);
            product.getImages().add(0, image);
            productImagesRepository.save(image);
            productRepository.save(product);

        } else {
            throw new RuntimeException("Product not found");
        }
    }

    public String getCoverByProductId(Integer id) {
        Optional<ProductImages> img = productImagesRepository.getCoverByProductId(id);
        String cover = null;
        if (img.isPresent()) {
            cover = img.get().getImagem();
        }
        return cover;
    }

    public List<String> getImagesByProductId(Integer id) {
        List<ProductImages> images = productImagesRepository.getImagesByProductId(id);
        return images.stream().map(ProductImages::getImagem)
                .collect(Collectors.toList());
    }
}
