package com.ecommerce.app.service;

import com.ecommerce.app.exception.DatabaseOperationException;
import com.ecommerce.app.exception.ResourceNotFoundException;
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
            try{
            imageRepository.delete(imagem.get());
            } catch (Exception e) { throw new DatabaseOperationException("Error deleting image"); }
        } else {
            throw new ResourceNotFoundException("Image not found with URL: " + imageUrl);
        }
    }

    @Transactional
    public void addImageCover(Integer id, String imageUrl) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

            ImageProduct image = new ImageProduct();

            image.setImagem(imageUrl);
            image.setProduct(product);
            product.getImages().add(0, image);
            try {
                imageRepository.save(image);
                productRepository.save(product);
            } catch (Exception e) {
                throw new DatabaseOperationException("Error adding image cover");
            }
    }

    public String getCoverByProductId(Integer id) {
        return imageRepository.getCoverByProductId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cover image not found for product ID: " + id))
                .getImagem();
    }

    public List<String> getImagesByProductId(Integer id) {
        try{
        List<ImageProduct> images = imageRepository.getImagesByProductId(id);
        return images.stream().map(ImageProduct::getImagem)
                .collect(Collectors.toList());
        } catch (Exception e) { throw new DatabaseOperationException("Error retrieving images by product ID: " + id); }
    }
}
