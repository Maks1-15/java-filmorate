package ru.yandex.practicum.filmorate.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.repository.impl.FilmRepositoryImpl;
import ru.yandex.practicum.filmorate.service.GenericService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmServiceImpl implements GenericService<Film> {

    private final FilmRepositoryImpl filmRepository;

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
}
