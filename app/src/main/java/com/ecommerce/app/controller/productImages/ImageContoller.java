package com.ecommerce.app.controller.productImages;

import com.ecommerce.app.dto.productImage.ImageDTO;
import com.ecommerce.app.service.productImages.ImageProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/images")
public class ImageContoller {

    @Autowired
    private ImageProductService imageProductService;

    public record ImageUrlRequest(String imageUrl) {}

    @DeleteMapping("/delete-image")
    public ResponseEntity<Void> deleteImage(@RequestBody ImageUrlRequest request) {
        imageProductService.deleteImageByUrl(request.imageUrl());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/add-cover")
    public void addCoverImage(@RequestBody ImageDTO cover) {
        imageProductService.addImageCover(cover.idProduct(), cover.imageUrl());
    }
}
