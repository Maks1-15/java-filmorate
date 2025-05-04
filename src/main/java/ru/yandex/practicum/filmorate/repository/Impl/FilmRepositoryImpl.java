package ru.yandex.practicum.filmorate.repository.Impl;

import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.repository.FilmRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class FilmRepositoryImpl implements FilmRepository {

    private final Map<Long, Film> films = new HashMap<>();
    private final AtomicInteger generateId = new AtomicInteger(1);

    @Override
    public void createFilm(Film film) {
        films.put((long) generateId.getAndIncrement(), film);
    }

    @Override
    public void updateFilm(Film film) {
        if (!films.containsKey(film.getId())) {
            return;
        }
        films.put(film.getId(), film);
    }

    @Override
    public List<Film> getAllFilms() {
        return films.values().stream().collect(Collectors.toList());
    }

    @Override
    public void deleteAllFilms() {
        films.clear();
    }
}
