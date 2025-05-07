package ru.yandex.practicum.filmorate.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.impl.UserRepositoryImpl;
import ru.yandex.practicum.filmorate.service.GenericService;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements GenericService<User> {

    private final UserRepositoryImpl userRepository;

    @Autowired
    public UserServiceImpl(UserRepositoryImpl userRepository) {
        this.userRepository = userRepository;
    }

    public User create(User user) {
        if (user.getName() == null) {
            user.setName(user.getLogin());
        }
        return userRepository.create(user);
    }

    public User update(User user) {
        return userRepository.update(user);
    }

    public List<User> getAll() {
        return userRepository.getAll();
    }

    public Optional<User> getById(Long id) {
        return userRepository.getById(id);
    }

    public void deleteAll() {
        userRepository.deleteAll();
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}