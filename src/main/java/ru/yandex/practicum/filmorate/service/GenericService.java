package ru.yandex.practicum.filmorate.service;

import java.util.List;
import java.util.Optional;

public interface GenericService<T> {
    T create(T entity);

    T update(T entity);

    List<T> getAll();

    Optional<T> getById(Long id);

    void deleteAll();

    void deleteById(Long id);
}
