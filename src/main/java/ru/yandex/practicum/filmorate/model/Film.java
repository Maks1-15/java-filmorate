package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.yandex.practicum.filmorate.model.validation.ReleaseDateValidation;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Builder(toBuilder = true)
public class Film {
    private int id; // id фильма

    @NotBlank(message = "Название фильма не может быть пустым")
    @NotNull(message = "Название фильма обязательно")
    private String name;

    @NotBlank(message = "Описание не может быть пустым")
    @Size(max = 200, message = "Максимальная длина описания — 200 символов")
    private String description;

    @NotNull(message = "Дата релиза обязательна")
    @Past(message = "Дата релиза должна быть в прошлом")
    @ReleaseDateValidation(message = "Дата релиза не может быть раньше 28 декабря 1895 года")
    private LocalDate releaseDate;

    @Positive(message = "Продолжительность должна быть положительной")
    @NotNull(message = "Продолжительность обязательна")
    private Integer duration;

    @NotNull(message = "Рейтинг MPA обязателен")
    private Mpa mpa;

    private Set<Genre> genres;
}

