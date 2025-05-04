package ru.yandex.practicum.filmorate.repository;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserRepository {
    void createUser(User user);

    void updateUser(User user);

    List<User> getAllUsers();

    void deleteAllUsers();
}
