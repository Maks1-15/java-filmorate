package ru.yandex.practicum.filmorate.service;

import java.util.List;

public interface GenericService<T> {
    T create(T entity);

    T update(T entity);

    List<T> getAll();
}
