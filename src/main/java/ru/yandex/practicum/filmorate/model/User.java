package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Data;

import java.time.LocalDate;

@Data
public class User {
    private Long id; // id пользователя
    // Валидация через метод
    private String name; // имя пользователя

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
