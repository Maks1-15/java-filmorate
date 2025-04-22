package ru.yandex.practicum.filmorate.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.Duration;
import java.time.LocalDate;

@Entity
@Table(name = "films")
@Data
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id; // id фильма
//    Long userId; // id пользователя, добавивший фильм // TODO

    @NotBlank
    String name; // Название фильма

    @Size(max = 200)
    String description; // Описание фильма

    //TODO
    LocalDate releaseDate; // Дата создания

    @Positive
    Duration duration; // Продолжительность фильма
}
