package ru.yandex.practicum.filmorate.repository.impl;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.EmailAlreadyExistsException;
import ru.yandex.practicum.filmorate.exception.EntityNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.GenericRepository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class UserRepositoryImpl implements GenericRepository<User> {

    private final Map<Long, User> users = new HashMap<>();
    private final Map<Long, Set<Long>> friends = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    // Метод для добавления пользователя в друзья к другому пользователю
    public void addFriend(Long userId, Long friendId) {
        // Добавляем первого в список друзей
        friends.computeIfAbsent(userId, k ->
                new HashSet<>()).add(friendId);
        // Добавляем второго в список друзей, обеспечивая взаимность
        friends.computeIfAbsent(friendId, k ->
                new HashSet<>()).add(userId);
    }

    // Метод для удаления пользователя из друзей другого пользователя
    public void removeFriend(Long userId, Long friendId) {
        // Проверяем наличие друга у первого и удаляем
        friends.getOrDefault(userId, Collections.emptySet()).remove(friendId);
        friends.getOrDefault(friendId, Collections.emptySet()).remove(userId);
    }

    // Метод для получения списка друзей пользователя
    public Set<Long> getFriends(Long userId) {
        if (!users.containsKey(userId)) {
            throw new EntityNotFoundException("Пользователь с ID " + userId + " не найден.");
        }
        return friends.get(userId);
    }

    // Метод, для получения общих друзей
    public List<Long> getMutualFriends(Long userId, Long otherUserId) {
        Set<Long> userFriends = new HashSet<>(getFriends(userId));
        userFriends.retainAll(getFriends(otherUserId));
        return new ArrayList<>(userFriends);
    }

    @Override
    public User create(User user) {
        if (existsByEmail(user.getEmail())) {
            throw new EmailAlreadyExistsException("Пользователь с email " + user.getEmail() + " уже существует.");
        }

        user.setId(idGenerator.getAndIncrement());
        friends.put(user.getId(), new HashSet<>());
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
    public List<User> getAll() {
        return new ArrayList<>(users.values());
    }

    public User getUserById(Long userId) {
        return users.get(userId);
    }

    // Метод уникальность email
    private boolean existsByEmail(String email) {
        return users.values().stream()
                .anyMatch(f -> f.getEmail().equalsIgnoreCase(email));
    }

    public void checkUsersExists(Long userId, Long friendId) {
        if (!users.containsKey(userId) || !friends.containsKey(friendId)) {
            throw new EntityNotFoundException("Пользователь(и) с таким id не найден(ы)");
        }
    }

    public void checkUserExists(Long userId) {
        if (!users.containsKey(userId)) {
            throw new EntityNotFoundException("Пользователь(и) с таким id не найден(ы)");
        }
    }

}