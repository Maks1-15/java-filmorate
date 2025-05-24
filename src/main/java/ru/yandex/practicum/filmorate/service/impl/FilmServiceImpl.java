package ru.yandex.practicum.filmorate.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.repository.impl.FilmRepositoryImpl;
import ru.yandex.practicum.filmorate.repository.impl.UserRepositoryImpl;
import ru.yandex.practicum.filmorate.service.GenericService;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FilmServiceImpl implements GenericService<Film> {

    private final FilmRepositoryImpl filmRepository;
    private final UserRepositoryImpl userRepository;

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

    public void addLike(Long filmId, Long userId) {
        userRepository.checkUserExists(userId);
        filmRepository.checkFilmExists(filmId);
        filmRepository.addLike(filmId, userId);
    }

    public void removeLike(Long filmId, Long userId) {
        userRepository.checkUserExists(userId);
        filmRepository.checkFilmExists(filmId);
        filmRepository.removeLike(filmId, userId);
    }

    public List<Film> getTopNFilms(int count) {
        if (count <= 0) {
            count = 10;
        }
        return filmRepository.getAll().stream()
                .sorted(Comparator.comparing(film -> filmRepository.getLikeCount(film.getId()), Comparator.reverseOrder()))
                .limit(count)
                .collect(Collectors.toList());
    }
}
