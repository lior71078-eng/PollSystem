package com.example.pollsystem.poll.repository;

import com.example.pollsystem.poll.PollAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface PollAnswerRepository extends JpaRepository<PollAnswer, Long> {
    void deleteAllByUserId(Long userId);
    List<PollAnswer> findAllByUserId(Long userId);

    long countByUserId(Long userId);
    long countByQuestionId(Long questionId);
    long countByQuestionIdAndSelectedOption(Long questionId, String selectedOption);

    @Query("SELECT pa.selectedOption, COUNT(pa) FROM PollAnswer pa " +
            "WHERE pa.questionId = :questionId GROUP BY pa.selectedOption")
    List<Object[]> countAnswersByOption(@Param("questionId") Long questionId);
}