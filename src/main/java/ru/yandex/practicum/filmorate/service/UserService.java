package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.UserRepository;

import java.util.List;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        log.debug("Creating user {}", user);
        User savedUser = userRepository.save(user);
        log.debug("User created with ID {}", savedUser.getId());
        return savedUser;
    }

    public User updateUser(User user) {
        log.debug("Updating user {}", user);
        User updatedUser = userRepository.save(user);
        log.debug("User updated with ID {}", updatedUser.getId());
        return updatedUser;
    }

    public List<User> getAllUser() {
        log.debug("Getting all users");
        List<User> users = userRepository.findAll();
        log.info("Found {} users", users.size());
        return users;
    }
}
