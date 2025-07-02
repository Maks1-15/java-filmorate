package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.EntityNotFoundException;
import ru.yandex.practicum.filmorate.exception.LikesException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.repository.film.FilmRepository;
import ru.yandex.practicum.filmorate.repository.genre.GenreRepository;
import ru.yandex.practicum.filmorate.repository.user.UserRepository;
import ru.yandex.practicum.filmorate.repository.mpa.MpaRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FilmService{
    private final FilmRepository filmRepository;
    private final UserRepository userRepository;
    private final MpaRepository mpaRepository;
    private final GenreRepository genreRepository;

    public Film createFilm(Film film) {
        validateMpaId(film);
        validateGenreId(film);
        return filmRepository.save(film);
    }

    public Film updateFilm(Film film) {
        validateFilmExists(film.getId());
        validateMpaId(film);
        validateGenreId(film);
        return filmRepository.save(film);
    }

    public List<Film> getAllFilms() {
        return filmRepository.findAll();
    }

    public void addLike(int filmId, int userId) {
        validateFilmExists(filmId);
        validateUserExists(userId);
        if (filmRepository.isLikeExists(filmId, userId)) {
            throw new LikesException("Лайк уже установлен!");
        }
        filmRepository.addLike(filmId, userId);
    }

    public void deleteLike(int filmId, int userId) {
        validateFilmExists(filmId);
        validateUserExists(userId);
        filmRepository.deleteLike(filmId, userId);
    }

    public Film getFilmById(int filmId) {
        validateFilmExists(filmId);
        return filmRepository.findById(filmId).get();
    }

    public List<Film> findMostLikedFilms(int count) {
        if (count <= 0) {
            throw new IllegalArgumentException("Count должен быть положительным числом");
        }
        return filmRepository.findMostLikedFilms(count);
    }

    // методы валидации

    private void validateFilmExists(long filmId) {
        if (filmRepository.findById(filmId).isEmpty()) {
            throw new EntityNotFoundException("Фильм с ID " + filmId + " не найден");
        }
    }

    private void validateUserExists(int userId) {
        if (userRepository.findById(userId).isEmpty()) {
            throw new EntityNotFoundException("Пользователь с ID " + userId + " не найден");
        }
    }

    private void validateMpaId(Film film) {
        if (film.getMpa() != null) {
            int mpaId = film.getMpa().getId();
            Optional<Mpa> mpaOpt = mpaRepository.findById(mpaId);
            if (mpaOpt.isEmpty()) {
                throw new EntityNotFoundException("MPA с ID = " + mpaId + " не найден.");
            }
        }
    }

    private void validateGenreId(Film film) {
        if (film.getGenres() != null) {
            for (Genre genre : film.getGenres()) {
                int id = genre.getId();
                if (genreRepository.findById(id).isEmpty()) {
                    throw new EntityNotFoundException("Жанр с ID = " + id + " не найден.");
                }
            }
        }
    }
}