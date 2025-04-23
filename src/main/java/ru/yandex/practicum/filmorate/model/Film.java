package ru.yandex.practicum.filmorate.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.yandex.practicum.filmorate.model.annotation.PositiveDuration;
import ru.yandex.practicum.filmorate.model.annotation.ReleaseDateValidation;

import java.time.Duration;
import java.time.LocalDate;

@Entity
@Table(name = "films", uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
@Data
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id; // id фильма
//    Long userId; // id пользователя, добавивший фильм // TODO
    // как я понимаю, потом мы будем связывать пользователя и фильмы, которые он добавил
    // Это лучше сделать также, как мы делали в прошлом фз, где у Epic есть все id Subtask?

    @NotBlank
    String name; // Название фильма

    @Size(max = 200)
    String description; // Описание фильма

    @ReleaseDateValidation
    LocalDate releaseDate; // Дата создания

    @PositiveDuration
    Duration duration; // Продолжительность фильма
}
