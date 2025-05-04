package ru.yandex.practicum.filmorate.repository;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmRepository {
    void createFilm(Film film);

    void updateFilm(Film film);

    List<Film> getAllFilms();

    void deleteAllFilms();
}
