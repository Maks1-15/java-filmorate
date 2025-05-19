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
}