package com.dailycodework.cookshare.service.recipe;

import com.dailycodework.cookshare.dto.ImageDto;
import com.dailycodework.cookshare.dto.RecipeDto;
import com.dailycodework.cookshare.dto.ReviewDto;
import com.dailycodework.cookshare.dto.UserDto;
import com.dailycodework.cookshare.model.Image;
import com.dailycodework.cookshare.model.Recipe;
import com.dailycodework.cookshare.model.Review;
import com.dailycodework.cookshare.model.User;
import com.dailycodework.cookshare.repository.ImageRepository;
import com.dailycodework.cookshare.repository.RecipeRepository;
import com.dailycodework.cookshare.repository.ReviewRepository;
import com.dailycodework.cookshare.repository.UserRepository;
import com.dailycodework.cookshare.request.CreateRecipeRequest;
import com.dailycodework.cookshare.request.RecipeUpdateRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RecipeService implements IRecipeService {
    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final ImageRepository imageRepository;
    private final ReviewRepository reviewRepository;

    @Override
    public Recipe createRecipe(CreateRecipeRequest request) {
        if(request == null || request.getUser() == null){
            throw new IllegalArgumentException("Invalid request");
        }
        User user = Optional.ofNullable(userRepository.findByUsername(request.getUser().getUsername()))
                .map(existingUser -> {
                    existingUser.getRecipe().add(request.getRecipe());
                    return existingUser;
                }).orElseGet(() -> {
                    User newUser = new User(request.getUser().getUsername());
                    userRepository.save(newUser);
                    return newUser;
                });
        Recipe recipe = IRecipeService.createRecipe(request, user);
        return recipeRepository.save(recipe);
    }

    @Override
    public Recipe updateRecipe(RecipeUpdateRequest request, Long recipeId) {
        Recipe recipe = getRecipeById(recipeId);
        Recipe theRecipe = IRecipeService.updateRecipe(recipe, request);
        return recipeRepository.save(theRecipe);
    }

    @Override
    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    @Override
    public Recipe getRecipeById(Long id) {
        return recipeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Recipe not found"));
    }

    @Override
    public void deleteRecipe(Long recipeId) {
        recipeRepository.deleteById(recipeId);

    }

    @Override
    public Set<String> getAllRecipeCategories() {
        return recipeRepository.findAll()
                .stream()
                .map(Recipe :: getCategory)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> getAllRecipeCuisine() {
        return recipeRepository.findAll()
                .stream()
                .map(Recipe :: getCuisine)
                .collect(Collectors.toSet());
    }
    @Override
     public List<RecipeDto> getConvertedRecipes(List<Recipe> recipes){
        return recipes.stream().map(this :: convertToDto).toList();
     }

     @Override
    public RecipeDto convertToDto(Recipe recipe){
        RecipeDto recipeDto = modelMapper.map(recipe, RecipeDto.class);
        UserDto userDto = modelMapper.map(recipe.getUser(), UserDto.class);
        Optional<Image> image  = Optional.ofNullable(imageRepository.findByRecipeId(recipe.getId()));
        image.map(img -> modelMapper.map(img, ImageDto.class)).ifPresent(recipeDto ::setImageDto);
        List<ReviewDto> reviews = reviewRepository.findAllByRecipeId(recipe.getId())
                .stream()
                .map(review -> modelMapper.map(review, ReviewDto.class)).toList();

        double averageReviews = recipe.calculateAverageRatings();
        int totalRateCount = recipe.getTotalRateCount();

         recipeDto.setTotalRateCount(totalRateCount);
         recipeDto.setAverageRating(averageReviews);

        recipeDto.setUser(userDto);
        recipeDto.setReviewDto(reviews);
        return recipeDto;
    }
}
