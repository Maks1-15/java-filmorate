package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.impl.UserServiceImpl;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    @PostMapping
    public ResponseEntity<User> create(@Valid @RequestBody User user) {
        log.info("Получен запрос на создание пользователя: {}", user);
        User createdUser = userService.create(user);
        log.info("Пользователь успешно создан с ID: {}", createdUser.getId());
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<User> update(@Valid @RequestBody User user) {
        log.info("Получен запрос на обновление пользователя: {}", user);
        User updatedUser = userService.update(user);
        if (updatedUser != null) {
            log.info("Пользователь успешно обновлен с ID: {}", updatedUser.getId());
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } else {
            log.warn("Пользователь с ID {} не найден для обновления.", user.getId());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        log.info("Получен запрос на получение всех пользователей.");
        List<User> users = userService.getAll();
        log.info("Возвращено {} пользователей.", users.size());
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public ResponseEntity<Void> addFriend(@PathVariable Long id, @PathVariable Long friendId) {
        log.info("Получен запрос на добавление в друзья пользователем.");
        userService.addFriend(id, friendId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public ResponseEntity<Void> removeFriend(@PathVariable Long id, @PathVariable Long friendId) {
        log.info("Получен запрос на удаление из друзей пользователем.");
        userService.removeFriend(id, friendId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}/friends")
    public ResponseEntity<List<User>> getFriends(@PathVariable Long id) {
        log.info("Получен запрос на получение списка друзей пользователя");
        List<User> friends = userService.getFriends(id);
        log.info("Возвращено {} друзей.", friends.size());
        return new ResponseEntity<>(friends, HttpStatus.OK);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public ResponseEntity<List<User>> getFriendsCommon(@PathVariable Long id, @PathVariable Long otherId) {
        log.info("Получен запрос на получение общих друзей пользователей");
        List<User> friends = userService.getMutualFriends(id, otherId);
        log.info("Возвращено общих {} друзей.", friends.size());
        return new ResponseEntity<>(friends, HttpStatus.OK);
    }
}