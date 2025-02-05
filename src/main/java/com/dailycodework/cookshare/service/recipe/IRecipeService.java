package com.dailycodework.cookshare.service.recipe;

import com.dailycodework.cookshare.dto.RecipeDto;
import com.dailycodework.cookshare.model.Recipe;
import com.dailycodework.cookshare.model.User;
import com.dailycodework.cookshare.request.CreateRecipeRequest;
import com.dailycodework.cookshare.request.RecipeUpdateRequest;

import java.util.List;
import java.util.Set;

public interface IRecipeService {
    Recipe createRecipe(CreateRecipeRequest request);

    List<Recipe> getAllRecipes();

    Recipe getRecipeById(Long id);

    Recipe updateRecipe(RecipeUpdateRequest request, Long recipeId);

    void deleteRecipe(Long recipeId);

    Set<String> getAllRecipeCategories();

    Set<String> getAllRecipeCuisine();


    static Recipe createRecipe(CreateRecipeRequest request, User user) {
        Recipe recipe = new Recipe();
        Recipe createRequest = request.getRecipe();
        recipe.setTitle(createRequest.getTitle());
        recipe.setCuisine(createRequest.getCuisine());
        recipe.setCategory(createRequest.getCategory());
        recipe.setInstruction(createRequest.getInstruction());
        recipe.setDescription(createRequest.getDescription());
        recipe.setPrepTime(createRequest.getPrepTime());
        recipe.setCookTime(createRequest.getCookTime());
        recipe.setIngredients(createRequest.getIngredients());
        recipe.setUser(user);
        return recipe;
    }

    static Recipe updateRecipe(Recipe existingRecipe, RecipeUpdateRequest request){
        existingRecipe.setTitle(request.getTitle());
        existingRecipe.setDescription(request.getDescription());
        existingRecipe.setInstruction(request.getInstruction());
        existingRecipe.setCuisine(request.getCuisine());
        existingRecipe.setPrepTime(request.getPrepTime());
        existingRecipe.setCookTime(request.getCookTime());
        existingRecipe.setCategory(request.getCategory());
        existingRecipe.setIngredients(request.getIngredients());
        return existingRecipe;
    }

    List<RecipeDto> getConvertedRecipes(List<Recipe> recipes);

    RecipeDto convertToDto(Recipe recipe);
}
