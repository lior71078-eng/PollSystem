package com.example.pollsystem.poll.repository;

import com.example.pollsystem.poll.Question;
import org.springframework.data.jpa.repository.JpaRepository;
public interface QuestionRepository extends JpaRepository<Question, Long> {
}