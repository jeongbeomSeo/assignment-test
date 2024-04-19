package com.example.practiceblog.repository;

import com.example.practiceblog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByLoginId(String loginId);
    User findByEmail(String email);
}
