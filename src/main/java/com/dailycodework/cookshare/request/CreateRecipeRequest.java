package com.dailycodework.cookshare.request;

import com.dailycodework.cookshare.model.Recipe;
import com.dailycodework.cookshare.model.User;
import lombok.Data;

@Data
public class CreateRecipeRequest {
    private Recipe recipe;
    private User user;
}
