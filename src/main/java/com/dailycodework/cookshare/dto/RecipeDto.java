package com.dailycodework.cookshare.dto;

import com.dailycodework.cookshare.model.User;
import lombok.Data;

@Data
public class RecipeDto {
    private Long id;
    private String title;
    private String instruction;
    private String prepTime;
    private String cookTime;
    private String category;
    private String description;
    private String cuisine;
    private UserDto user;
}
