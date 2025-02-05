package com.dailycodework.cookshare.repository;

import com.dailycodework.cookshare.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
  User findByUsername(String username);
}
