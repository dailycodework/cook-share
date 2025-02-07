package com.dailycodework.cookshare.service.image;

import com.dailycodework.cookshare.dto.ImageDto;
import com.dailycodework.cookshare.model.Image;
import org.springframework.web.multipart.MultipartFile;

public interface IImageService {
    Image getImageById(Long imageId);
    void deleteImage(Long imageId);
    void updateImage(MultipartFile file, Long imageId);
    ImageDto saveImage(Long recipeId, MultipartFile file);
}
