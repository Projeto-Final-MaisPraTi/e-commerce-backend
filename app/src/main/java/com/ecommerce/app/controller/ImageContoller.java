package com.ecommerce.app.controller;

import com.ecommerce.app.dto.produtos.ImageDTO;
import com.ecommerce.app.service.ImageProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/images")
public class ImageContoller {

    @Autowired
    private ImageProductService imageProductService;

    @DeleteMapping("/delete-image")
    public ResponseEntity<Void> deleteImage(@RequestBody String imageUrl) {
        imageProductService.deleteImageByUrl(imageUrl);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/add-cover")
    public void addCoverImage(@RequestBody ImageDTO cover) {
        imageProductService.addImageCover(cover.idProduct(), cover.imageUrl());
    }
}
