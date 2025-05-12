package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.Data;
import ru.yandex.practicum.filmorate.model.validation.ReleaseDateValidation;

import java.time.LocalDate;

@Data
public class Film {
    private Long id; // id фильма

    @NotBlank
    @NotNull
    private String name; // название фильма

    @Size(max = 200)
    @NotBlank
    private String description; // описание фильма

    @Past
    @ReleaseDateValidation
    @NotNull
    private LocalDate releaseDate; // дата релиза

    @Positive
    @NotNull
    private Integer duration; // продолжительность фильма
}
