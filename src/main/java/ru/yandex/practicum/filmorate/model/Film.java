package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.yandex.practicum.filmorate.model.validation.ReleaseDateValidation;

import java.time.LocalDate;

@Data
public class Film {
    private Long id; // id фильма

    @NotBlank
    private String title; // название фильма

    @Size(max = 200)
    @NotBlank
    private String description; // описание фильма

    @Past
    @NotBlank
    @ReleaseDateValidation
    private LocalDate releaseDate; // дата релиза

    @Positive
    @NotBlank
    private Integer duration; // продолжительность фильма
}
