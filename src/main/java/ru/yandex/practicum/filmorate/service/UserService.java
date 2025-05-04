package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.UserRepository;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        userRepository.createUser(user);
        return user;
    }

    public User updateUser(User user) {
        userRepository.updateUser(user);
        return user;
    }

    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    public void deleteAllUsers() {
        userRepository.deleteAllUsers();
    }
}
