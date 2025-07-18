package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.impl.FilmServiceImpl;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmController {

    private final FilmServiceImpl filmService;

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
        Film updatedFilm = filmService.update(film);
        if (updatedFilm != null) {
            log.info("Фильм успешно обновлен с ID: {}", updatedFilm.getId());
            return new ResponseEntity<>(updatedFilm, HttpStatus.OK);
        } else {
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


    @PutMapping("/{id}/like/{userId}")
    public ResponseEntity<Void> addLike(@PathVariable Long id, @PathVariable Long userId) {
        log.info("Получен запрос на добавления лайка");
        filmService.addLike(id, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public ResponseEntity<Void> removeLike(@PathVariable Long id, @PathVariable Long userId) {
        log.info("Получен запрос на удаление лайка");
        filmService.removeLike(id, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/popular")
    public ResponseEntity<List<Film>> getPopularFilms(@RequestParam(required = false, defaultValue = "10") Integer count) {
        log.info("Получен запрос получение {} фильмов", count);
        List<Film> popularFilms = filmService.getTopNFilms(count);
        return new ResponseEntity<>(popularFilms, HttpStatus.OK);
    }
}