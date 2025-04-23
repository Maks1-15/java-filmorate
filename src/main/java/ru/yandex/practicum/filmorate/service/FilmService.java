package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.repository.FilmRepository;

import java.util.List;

@Service
@Slf4j
public class FilmService {
    private final FilmRepository filmRepository;

    @Autowired
    public FilmService(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    public Film createFilm(Film film) {
        log.debug("Creating film: {}", film);
        Film savedFilm = filmRepository.save(film);
        log.info("Film created with ID: {}", savedFilm.getId());
        return savedFilm;
    }

    public Film updateFilm(Film film) {
        log.debug("Updating film: {}", film);
        Film updatedFilm = filmRepository.save(film);
        log.info("Film updated with ID: {}", updatedFilm.getId());
        return updatedFilm;
    }

    public List<Film> getAllFilms() {
        log.debug("Getting all films");
        List<Film> films = filmRepository.findAll();
        log.info("Found {} films", films.size());
        return films;
    }
}
