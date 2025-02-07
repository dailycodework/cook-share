package com.dailycodework.cookshare.controller;

import com.dailycodework.cookshare.dto.ImageDto;
import com.dailycodework.cookshare.model.Image;
import com.dailycodework.cookshare.service.image.IImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/images")
public class ImageController {
    private final IImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<ImageDto> uploadImage(
                                                @RequestParam("file") MultipartFile file,
                                                @RequestParam("recipeId") Long recipeId){
        ImageDto savedImage = imageService.saveImage(recipeId, file);
        return ResponseEntity.ok(savedImage);
    }

    @PutMapping("/image/{imageId}/update")
    public ResponseEntity<String> updateImage(@PathVariable Long imageId, @RequestBody MultipartFile file) {
        imageService.updateImage(file, imageId);
        return ResponseEntity.ok("Image updated successfully!");
    }

    @DeleteMapping("/image/{imageId}/delete")
    public ResponseEntity<String> deleteImage(@PathVariable Long imageId) {
        imageService.deleteImage(imageId);
        return ResponseEntity.ok("Delete Image success!");
    }

    @GetMapping("/image/download/{imageId}")
    public ResponseEntity<Resource> downloadImage(@PathVariable  Long imageId) throws SQLException {
        Image image = imageService.getImageById(imageId);
        ByteArrayResource resource = new ByteArrayResource(image.getImage().getBytes(1, (int) image.getImage().length()));
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(image.getFileTye()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +image.getFileName()
                +"\"").body(resource);
    }

}
