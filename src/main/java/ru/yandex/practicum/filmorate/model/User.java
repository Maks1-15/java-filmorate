package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder(toBuilder = true)
public class User {
    private int id;

    // если имя не указано, используется логин
    private String name;

    @Email(message = "Email должен быть в формате user@..")
    @NotBlank(message = "Email не может быть пустым")
    @NotNull(message = "Email обязателен для заполнения")
    private String email;

    @NotBlank(message = "Логин не может быть пустым")
    @NotNull(message = "Логин обязателен для заполнения")
    private String login;

    @Past(message = "Дата рождения должна быть в прошлом")
    @NotNull(message = "Дата рождения обязательна для заполнения")
    private LocalDate birthday;
}
