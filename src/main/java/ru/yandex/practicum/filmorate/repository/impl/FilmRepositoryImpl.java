package ru.yandex.practicum.filmorate.repository.impl;

import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.EntityNotFoundException;
import ru.yandex.practicum.filmorate.exception.FilmAlreadyExistsException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.repository.GenericRepository;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class FilmRepositoryImpl implements GenericRepository<Film> {

    private final Map<Long, Film> films = new HashMap<>();
    private final AtomicInteger idGenerator = new AtomicInteger(1);

    @Override
    public Film create(Film film) {
        if (existsByTitle(film.getName())) {
            throw new FilmAlreadyExistsException("Фильм с названием '" + film.getName() + "' уже существует.");
        }

        Long id = (long) idGenerator.getAndIncrement();
        film.setId(id);
        films.put(id, film);
        return film;
    }

    @Override
    public Film update(Film film) {
        if (!films.containsKey(film.getId())) {
            throw new EntityNotFoundException("Фильм с ID " + film.getId() + " не найден.");
        }

        films.put(film.getId(), film);
        return film;
    }

    @Override
    public List<Film> getAll() {
        return new ArrayList<>(films.values());
    }

    private boolean existsByTitle(String title) {
        return films.values().stream()
                .anyMatch(f -> f.getName().equalsIgnoreCase(title));
    }
}
