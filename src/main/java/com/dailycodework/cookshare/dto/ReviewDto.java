package com.dailycodework.cookshare.dto;

import lombok.Data;

@Data
public class ReviewDto {
    private Long id;
    private int star;
    private String feedBack;
    private UserDto user;
}
