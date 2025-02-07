package com.dailycodework.cookshare.service.review;

import com.dailycodework.cookshare.model.Recipe;
import com.dailycodework.cookshare.model.Review;
import com.dailycodework.cookshare.model.User;
import com.dailycodework.cookshare.repository.RecipeRepository;
import com.dailycodework.cookshare.repository.ReviewRepository;
import com.dailycodework.cookshare.repository.UserRepository;
import com.dailycodework.cookshare.request.ReviewRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService implements IReviewService {

    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;

    @Override
    public void addReview(Long recipeId, ReviewRequest request) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new EntityNotFoundException("Recipe not found"));

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found!"));

          reviewRepository.findByRecipeIdAndUserId(recipeId, user.getId())
                  .ifPresentOrElse(existingReview -> updateReview(existingReview, request),
                          () -> createNewReview(request, recipe));

          double averageRating = recipe.calculateAverageRatings();
          recipe.setAverageRating(averageRating);
          int totalRateCount = getTotalReviews(recipeId);
          recipe.setTotalRateCount(totalRateCount);
          recipeRepository.save(recipe);
    }
    

    private void updateReview(Review existingReview, ReviewRequest request){
        existingReview.setStars(request.getStars());
        existingReview.setFeedBack(request.getFeedBack());
        reviewRepository.save(existingReview);
    }

    private void createNewReview(ReviewRequest request, Recipe recipe){
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found!"));
        Review newReview = new Review();
        newReview.setUser(user);
        newReview.setStars(request.getStars());
        newReview.setFeedBack(request.getFeedBack());
        newReview.setRecipe(recipe);
        newReview.setUser(user);
        reviewRepository.save(newReview);
    }

    @Override
    public void deleteReview(Long recipeId, Long reviewId) {
        reviewRepository.findById(reviewId).ifPresentOrElse(review -> {
            Recipe recipe = recipeRepository.findById(recipeId)
                    .orElseThrow(() -> new EntityNotFoundException("Recipe not found!"));
            recipe.getReviews().remove(review);
            recipeRepository.save(recipe);

            reviewRepository.delete(review);
        }, () -> {
            throw  new EntityNotFoundException("Review not found!");
        });
    }

    @Override
    public int getTotalReviews(Long recipeId) {
        return  reviewRepository.countAllByRecipeId(recipeId);

    }
}
