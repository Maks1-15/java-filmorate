package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.EmailAlreadyExistsException;
import ru.yandex.practicum.filmorate.exception.EntityNotFoundException;
import ru.yandex.practicum.filmorate.exception.FriendshipException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.user.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User createUser(User user) {
        User finalUser = user;
        userRepository.findByEmail(user.getEmail())
                .ifPresent(u -> {
                    throw new EmailAlreadyExistsException(finalUser.getEmail());
                });

        // Если у пользователя не указано имя, присваиваем полю name значение поля login
        if (user.getName() == null || user.getName().isBlank()) {
            user = user.toBuilder().name(user.getLogin()).build();
        }

        return userRepository.save(user);
    }

    public User updateUser(User user) {
        return userRepository.findById(user.getId())
                .map(existingUser -> userRepository.save(user))
                .orElseThrow(() -> new EntityNotFoundException(
                        "Обновление невозможно. Пользователь с ID " + user.getId() + " не найден."));
    }

    public User getUserById(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь с ID " + id + " не найден."));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void confirmFriendship(int userId, int friendId) {
        validateUsersExist(userId, friendId);

        // проверяем, что запрос на дружбу был от friendId к userId
        if (!userRepository.isValidFriendRequest(friendId, userId)) {
            throw new EntityNotFoundException("Не найден запрос от указанного пользователя: " + friendId);
        }

        userRepository.confirmFriendship(userId, friendId);
    }

    public List<User> getCommonFriends(int userId, int otherUserId) {
        validateUsersExist(userId, otherUserId);
        return userRepository.getCommonFriends(userId, otherUserId);
    }

    public List<User> getUserFriends(int id) {
        validateUserExists(id);
        return userRepository.getFriends(id);
    }

    public void addFriends(int userId, int friendId) {
        validateBasicRequest(userId, friendId);

        if (userRepository.existsFriendship(userId, friendId)) {
            throw new FriendshipException("Пользователи уже являются друзьями.");
        }
        // проверяем на повторный запрос
        if (userRepository.isValidFriendRequest(userId, friendId)) {
            throw new FriendshipException("Нельзя отправить запрос на добавление в друзья дважды.");
        }
        // проверяем, есть ли неподтвержденный запрос от friendId к userId
        if (userRepository.isValidFriendRequest(friendId, userId)) {
            userRepository.confirmFriendship(userId, friendId);
        } else if (!userRepository.isValidFriendRequest(userId, friendId)) { // Если запроса нет - создаем новый
            userRepository.addFriends(userId, friendId, false);
        }
    }

    public void removeFriend(int userId, int friendId) {
        validateBasicRequest(userId, friendId);
        userRepository.removeFriends(userId, friendId);
    }

    // вспомогательные методы валидации
    private void validateUserExists(int userId) {
        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException("Пользователь с ID " + userId + " не найден.");
        }
    }

    private void validateUsersExist(int userId1, int userId2) {
        validateUserExists(userId1);
        validateUserExists(userId2);
    }

    private void validateBasicRequest(int userId, int friendId) {
        if (userId < 1 || friendId < 1) {
            throw new IllegalArgumentException("ID пользователя должен быть положительным числом.");
        }
        if (userId == friendId) {
            throw new FriendshipException("ID пользователей должны отличаться.");
        }
        validateUsersExist(userId, friendId);
    }
}