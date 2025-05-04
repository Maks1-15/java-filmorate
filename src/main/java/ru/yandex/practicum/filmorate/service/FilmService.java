package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.repository.FilmRepository;

import java.util.List;

@Service
public class FilmService {

    private final FilmRepository filmRepository;

    @Autowired
    public FilmService(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    public Film createFilm(Film film) {
        filmRepository.createFilm(film);
        return film;
    }

    public Film updateFilm(Film film) {
        filmRepository.updateFilm(film);
        return film;
    }

    public List<Film> getAllFilms() {
        return filmRepository.getAllFilms();
    }

    public void deleteAllFilms() {
        filmRepository.deleteAllFilms();
    }
}
