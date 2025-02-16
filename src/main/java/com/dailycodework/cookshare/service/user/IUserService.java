package com.dailycodework.cookshare.service.user;

import com.dailycodework.cookshare.model.User;

public interface IUserService {

  User registerUser(User user);

  String findByUsername(String username);
}
