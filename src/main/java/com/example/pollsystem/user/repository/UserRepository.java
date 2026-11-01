package com.example.pollsystem.user.repository;

import com.example.pollsystem.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}