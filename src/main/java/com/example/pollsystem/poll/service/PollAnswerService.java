
package com.example.pollsystem.poll.service;

import com.example.pollsystem.poll.PollAnswer;
import com.example.pollsystem.poll.Question;
import com.example.pollsystem.poll.repository.PollAnswerRepository;
import com.example.pollsystem.poll.repository.QuestionRepository;
import com.example.pollsystem.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.ArrayList;

@Service
public class PollAnswerService {
    private final PollAnswerRepository pollAnswerRepository;
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    public PollAnswerService(
            PollAnswerRepository pollAnswerRepository,
            UserRepository userRepository,
            QuestionRepository questionRepository) {
        this.pollAnswerRepository = pollAnswerRepository;
        this.userRepository = userRepository;
        this.questionRepository = questionRepository;
    }

    public PollAnswer createAnswer(PollAnswer answer) {
        if (!userRepository.existsById(answer.getUserId())) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "User not found");
        }

        if (!questionRepository.existsById(answer.getQuestionId())) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Question not found");
        }

        return pollAnswerRepository.save(answer);
    }

    public List<PollAnswer> getAllAnswers() {
        return pollAnswerRepository.findAll();
    }

    public PollAnswer getAnswerById(Long id) {
        return pollAnswerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Answer not found"));
    }

    public PollAnswer updateAnswer(Long id, PollAnswer answer) {
        PollAnswer existingAnswer = getAnswerById(id);
        if (!userRepository.existsById(answer.getUserId())) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "User not found");
        }

        if (!questionRepository.existsById(answer.getQuestionId())) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Question not found");
        }

        existingAnswer.setUserId(answer.getUserId());
        existingAnswer.setQuestionId(answer.getQuestionId());
        existingAnswer.setSelectedOption(answer.getSelectedOption());
        return pollAnswerRepository.save(existingAnswer);
    }

    public void deleteAnswer(Long id) {
        if (!pollAnswerRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Answer not found");
        }

        pollAnswerRepository.deleteById(id);
    }

    public void deleteAnswersByUserId(Long userId) {
        pollAnswerRepository.deleteAllByUserId(userId);
    }

    public Map<String, Long> getQuestionStatistics(Long questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Question not found"));
        Map<String, Long> statistics = new LinkedHashMap<>();
        statistics.put("option1",
                pollAnswerRepository.countByQuestionIdAndSelectedOption(
                        questionId, question.getOption1()));
        statistics.put("option2",
                pollAnswerRepository.countByQuestionIdAndSelectedOption(
                        questionId, question.getOption2()));
        statistics.put("option3",
                pollAnswerRepository.countByQuestionIdAndSelectedOption(
                        questionId, question.getOption3()));
        statistics.put("option4",
                pollAnswerRepository.countByQuestionIdAndSelectedOption(
                        questionId, question.getOption4()));
        return statistics;
    }

    public long getTotalAnswers(Long questionId) {
        return pollAnswerRepository.countByQuestionId(questionId);
    }

    public List<PollAnswer> getUserAnswers(Long userId) {
        return pollAnswerRepository.findAllByUserId(userId);
    }

    public long getUserAnswerCount(Long userId) {
        return pollAnswerRepository.countByUserId(userId);
    }

    public List<Map<String, Object>> getAllQuestionsStatistics() {
        List<Question> questions = questionRepository.findAll();
        List<Map<String, Object>> result = new ArrayList<>();
        for (Question question : questions) {
            Map<String, Object> questionStatistics = new LinkedHashMap<>();
            questionStatistics.put("question", question);
            questionStatistics.put(
                    "statistics",
                    getQuestionStatistics(question.getId()));
            result.add(questionStatistics);
        }

        return result;
    }
}
