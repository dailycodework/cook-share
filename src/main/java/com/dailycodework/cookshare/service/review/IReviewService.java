package com.dailycodework.cookshare.service.review;

import com.dailycodework.cookshare.request.ReviewRequest;

public interface IReviewService {
    void addReview(Long recipeId, ReviewRequest request);
    void deleteReview(Long recipeId, Long reviewId);
    int getTotalReviews(Long recipeId);
}
