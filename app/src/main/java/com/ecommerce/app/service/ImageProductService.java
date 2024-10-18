package com.ecommerce.app.service;

import com.ecommerce.app.model.ImageProduct;
import com.ecommerce.app.model.Product;
import com.ecommerce.app.repository.ImageRepository;
import com.ecommerce.app.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ImageProductService {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public void deleteImageByUrl(String imageUrl) {
        imageRepository.deleteByImagem(imageUrl);
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
}
