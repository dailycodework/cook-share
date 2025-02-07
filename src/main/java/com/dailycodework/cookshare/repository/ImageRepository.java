package com.dailycodework.cookshare.repository;

import com.dailycodework.cookshare.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
    Image findByRecipeId(Long recipeId);
}
