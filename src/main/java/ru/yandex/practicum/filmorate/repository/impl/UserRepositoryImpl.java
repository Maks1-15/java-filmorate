package ru.yandex.practicum.filmorate.repository.impl;

import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.EmailAlreadyExistsException;
import ru.yandex.practicum.filmorate.exception.EntityNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.GenericRepository;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class UserRepositoryImpl implements GenericRepository<User> {

    private final Map<Long, User> users = new HashMap<>();
    private final AtomicInteger idGenerator = new AtomicInteger(1);

    @Override
    public User create(User user) {
        if (existsByEmail(user.getEmail())) {
            throw new EmailAlreadyExistsException("Пользователь с email " + user.getEmail() + " уже существует.");
        }

        user.setId((long) idGenerator.getAndIncrement());
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User update(User user) {
        if (!users.containsKey(user.getId())) {
            throw new EntityNotFoundException("Пользователь с ID " + user.getId() + " не найден.");
        }

        users.put(user.getId(), user);
        return user;
    }

    @Override
    public void deleteAll() {
        users.clear();
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public Optional<User> getById(Long id) {
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public void deleteById(Long id) {
        if (!users.containsKey(id)) {
            throw new EntityNotFoundException("Пользователь с ID " + id + " не найден.");
        }
        users.remove(id);
    }

    private boolean existsByEmail(String email) {
        return users.values().stream()
                .anyMatch(f -> f.getEmail().equalsIgnoreCase(email));
    }

}