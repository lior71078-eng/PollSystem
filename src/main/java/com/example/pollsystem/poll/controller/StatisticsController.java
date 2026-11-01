package com.example.pollsystem.poll.controller;

import com.example.pollsystem.poll.PollAnswer;
import com.example.pollsystem.poll.service.PollAnswerService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {

    private final PollAnswerService pollAnswerService;
    public StatisticsController(PollAnswerService pollAnswerService) {
        this.pollAnswerService = pollAnswerService;
    }

    @GetMapping("/questions/{questionId}/options")
    public Map<String, Long> getQuestionStatistics(
            @PathVariable Long questionId) {
        return pollAnswerService.getQuestionStatistics(questionId);
    }

    @GetMapping("/questions/{questionId}/total")
    public long getTotalAnswers(@PathVariable Long questionId) {
        return pollAnswerService.getTotalAnswers(questionId);
    }

    @GetMapping("/users/{userId}/answers")
    public List<PollAnswer> getUserAnswers(@PathVariable Long userId) {
        return pollAnswerService.getUserAnswers(userId);
    }

    @GetMapping("/users/{userId}/count")
    public long getUserAnswerCount(@PathVariable Long userId) {
        return pollAnswerService.getUserAnswerCount(userId);
    }

    @GetMapping("/questions")
    public List<Map<String, Object>> getAllQuestionsStatistics() {
        return pollAnswerService.getAllQuestionsStatistics();
    }
}