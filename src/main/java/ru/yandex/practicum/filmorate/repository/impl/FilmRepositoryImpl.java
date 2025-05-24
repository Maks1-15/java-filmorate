package ru.yandex.practicum.filmorate.repository.impl;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.EntityNotFoundException;
import ru.yandex.practicum.filmorate.exception.FilmAlreadyExistsException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.repository.GenericRepository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class FilmRepositoryImpl implements GenericRepository<Film> {

    private final Map<Long, Film> films = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);
    private final Map<Long, Set<Long>> filmLikes = new HashMap<>();

    // Метод для добавления лайка фильму от пользователя
    public void addLike(Long filmId, Long userId) {
        filmLikes.computeIfAbsent(filmId, k ->
                new HashSet<>()).add(userId);
    }

    // Метод для удаления лайка фильму от пользователя
    public void removeLike(Long filmId, Long userId) {
        Set<Long> likes = filmLikes.get(filmId);
        if (likes != null) {
            likes.remove(userId);
        }
    }

    // Метод для получения количества лайков фильма
    public Long getLikeCount(Long filmId) {
        return (long) filmLikes.getOrDefault(filmId, Collections.emptySet()).size();
    }


    @Override
    public Film create(Film film) {
        if (existsByTitle(film.getName())) {
            throw new FilmAlreadyExistsException("Фильм с названием '" + film.getName() + "' уже существует.");
        }

        Long id = idGenerator.getAndIncrement();
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

    // Метод уникальности названия фильма
    private boolean existsByTitle(String title) {
        return films.values().stream()
                .anyMatch(f -> f.getName().equalsIgnoreCase(title));
    }

    public void checkFilmExists(Long filmId) {
        if (!films.containsKey(filmId)) {
            throw new EntityNotFoundException("Фильма с таким id " + filmId + " не существует");
        }
    }
}
