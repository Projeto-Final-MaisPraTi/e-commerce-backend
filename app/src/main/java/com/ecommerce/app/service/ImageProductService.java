package com.ecommerce.app.service;

import com.ecommerce.app.model.ImageProduct;
import com.ecommerce.app.model.Product;
import com.ecommerce.app.repository.ImageRepository;
import com.ecommerce.app.repository.ProductRepository;
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
    private ImageRepository imageRepository;

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public void deleteImageByUrl(String imageUrl) {
        Optional<ImageProduct> imagem = imageRepository.findByImagem(imageUrl);
        if (imagem.isPresent()) {
            Product product = imagem.get().getProduct();
            product.getImages().remove(imagem.get());
            imageRepository.delete(imagem.get());
        } else {
            throw new EntityNotFoundException("Erro ao buscar imagem " + imageUrl);
        }
    }

    @Transactional
    public void addImageCover(Integer id, String imageUrl) {
        Optional <Product> optionalProduct = productRepository.findById(id);

        if (optionalProduct.isPresent()) {

            Product product = optionalProduct.get();
            ImageProduct image = new ImageProduct();

            image.setImagem(imageUrl);
            image.setProduct(product);
            product.getImages().add(0, image);
            imageRepository.save(image);
            productRepository.save(product);

        } else {
            throw new RuntimeException("Product not found");
        }
    }

    public String getCoverByProductId(Integer id) {
        Optional<ImageProduct> img = imageRepository.getCoverByProductId(id);
        String cover = null;
        if (img.isPresent()) {
            cover = img.get().getImagem();
        }
        return cover;
    }

    public List<String> getImagesByProductId(Integer id) {
        List<ImageProduct> images = imageRepository.getImagesByProductId(id);
        return images.stream().map(ImageProduct::getImagem)
                .collect(Collectors.toList());
    }
}
