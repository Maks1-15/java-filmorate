package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class User {
    private Long id; // id пользователя
    // Валидация через метод
    private String name; // имя пользоФвателя

    @Email
    @NotBlank
    @NotNull
    private String email; // email пользователя

    @NotBlank
    @NotNull
    private String login; // логин пользователя

    @Past
    @NotNull
    private LocalDate birthday; // др пользователя
}
