package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.EntityNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.impl.FilmServiceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    private final FilmServiceImpl filmService;

    @Autowired
    public FilmController(FilmServiceImpl filmService) {
        this.filmService = filmService;
    }

    @PostMapping
    public ResponseEntity<Film> create(@Valid @RequestBody Film film) {
        log.info("Получен запрос на создание фильма: {}", film);
        Film createdFilm = filmService.create(film);
        log.info("Фильм успешно создан с ID: {}", createdFilm.getId());
        return new ResponseEntity<>(createdFilm, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Film> update(@Valid @RequestBody Film film) {
        log.info("Получен запрос на обновление фильма: {}", film);
        try {
            Film updatedFilm = filmService.update(film);
            log.info("Фильм успешно обновлен с ID: {}", updatedFilm.getId());
            return new ResponseEntity<>(updatedFilm, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            log.warn("Фильм с ID {} не найден для обновления.", film.getId());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<Film>> getAll() {
        log.info("Получен запрос на получение всех фильмов.");
        List<Film> films = filmService.getAll();
        log.info("Возвращено {} фильмов.", films.size());
        return new ResponseEntity<>(films, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Film> getById(@PathVariable Long id) {
        log.info("Получен запрос на получение фильма с ID: {}", id);
        Optional<Film> film = filmService.getById(id);
        if (film.isPresent()) {
            log.info("Фильм с ID {} найден.", id);
            return new ResponseEntity<>(film.get(), HttpStatus.OK);
        } else {
            log.warn("Фильм с ID {} не найден.", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAll() {
        log.info("Получен запрос на удаление всех фильмов.");
        filmService.deleteAll();
        log.info("Все фильмы удалены.");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        log.info("Получен запрос на удаление фильма с ID: {}", id);
        try {
            filmService.deleteById(id);
            log.info("Фильм с ID {} удален.", id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            log.warn("Фильм с ID {} не найден для удаления.", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Обработчик исключений для валидации
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        log.warn("Ошибка валидации данных: {}", ex.getMessage());
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    // Обработчик исключений для EntityNotFoundException
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException ex) {
        log.warn("Entity not found: {}", ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    // Глобальный обработчик исключений
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGlobalExceptions(Exception ex) {
        log.error("Произошла ошибка: {}", ex.getMessage(), ex); // Log stack trace
        return new ResponseEntity<>("Произошла внутренняя ошибка сервера.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}