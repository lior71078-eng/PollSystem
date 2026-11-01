package com.example.pollsystem.user.service;

import com.example.pollsystem.user.User;
import com.example.pollsystem.user.repository.UserRepository;
import com.example.pollsystem.poll.service.PollAnswerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PollAnswerService pollAnswerService;

    public UserService(
            UserRepository userRepository,
            PollAnswerService pollAnswerService) {
        this.userRepository = userRepository;
        this.pollAnswerService = pollAnswerService;
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User updateUser(Long id, User user) {
        user.setId(id);
        return userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }

        pollAnswerService.deleteAnswersByUserId(id);
        userRepository.deleteById(id);
    }
}