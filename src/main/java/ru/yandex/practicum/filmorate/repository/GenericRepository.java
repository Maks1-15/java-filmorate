package ru.yandex.practicum.filmorate.repository;

import java.util.List;
import java.util.Optional;

public interface GenericRepository<T> {
    T create(T entity);

    T update(T entity);

    List<T> getAll();
}
