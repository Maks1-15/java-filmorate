package ru.yandex.practicum.filmorate.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.impl.UserRepositoryImpl;
import ru.yandex.practicum.filmorate.service.GenericService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements GenericService<User> {

    private final UserRepositoryImpl userRepository;

    @Override
    public User create(User user) {
        if (user.getName() == null) {
            user.setName(user.getLogin());
        }
        return userRepository.create(user);
    }

    @Override
    public User update(User user) {
        return userRepository.update(user);
    }

    @Override
    public List<User> getAll() {
        return userRepository.getAll();
    }
}