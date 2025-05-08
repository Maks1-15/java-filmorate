package ru.yandex.practicum.filmorate.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.repository.GenericRepository;
import ru.yandex.practicum.filmorate.repository.impl.FilmRepositoryImpl;

import java.util.List;
import java.util.Optional;

@Service
public class FilmServiceImpl implements GenericRepository<Film> {

    private final FilmRepositoryImpl filmRepository;

    @Autowired
    public FilmServiceImpl(FilmRepositoryImpl filmRepository) {
        this.filmRepository = filmRepository;
    }

    @Override
    public Film create(Film film) {
        return filmRepository.create(film);
    }

    @Override
    public Film update(Film film) {
        return filmRepository.update(film);
    }

    @Override
    public List<Film> getAll() {
        return filmRepository.getAll();
    }

    @Override
    public Optional<Film> getById(Long id) {
        return filmRepository.getById(id);
    }

    @Override
    public void deleteAll() {
        filmRepository.deleteAll();
    }

    @Override
    public void deleteById(Long id) {
        filmRepository.deleteById(id);
    }
}
