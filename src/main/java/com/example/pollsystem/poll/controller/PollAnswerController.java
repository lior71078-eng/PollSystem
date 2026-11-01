package com.example.pollsystem.poll.controller;

import com.example.pollsystem.poll.PollAnswer;
import com.example.pollsystem.poll.service.PollAnswerService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/answers")
public class PollAnswerController {

    private final PollAnswerService pollAnswerService;
    public PollAnswerController(PollAnswerService pollAnswerService) {
        this.pollAnswerService = pollAnswerService;
    }

    @PostMapping
    public PollAnswer createAnswer(@RequestBody PollAnswer answer) {
        return pollAnswerService.createAnswer(answer);
    }

    @GetMapping
    public List<PollAnswer> getAllAnswers() {
        return pollAnswerService.getAllAnswers();
    }

    @GetMapping("/{id}")
    public PollAnswer getAnswerById(@PathVariable Long id) {
        return pollAnswerService.getAnswerById(id);
    }

    @PutMapping("/{id}")
    public PollAnswer updateAnswer(
            @PathVariable Long id,
            @RequestBody PollAnswer answer) {
        return pollAnswerService.updateAnswer(id, answer);
    }

    @DeleteMapping("/{id}")
    public void deleteAnswer(@PathVariable Long id) {
        pollAnswerService.deleteAnswer(id);
    }
}
