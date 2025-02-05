package com.dailycodework.cookshare.repository;

import com.dailycodework.cookshare.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
}
