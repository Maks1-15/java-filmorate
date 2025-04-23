package ru.yandex.practicum.filmorate.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = {"email", "name"})})
@Data

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id; // id пользователя

    @NotNull
    // по условию поле может быть пустым, и таком случае name = login, но я не совсем понимаю как это реализовать
    // Проверка осуществляется с помощью @Valid в контроллере и если какое то поле не соответствует, то выкидывается
    // исключение. Надо заранее проверять поле name? или пытаться в контроллере обработать? точнее в service
    String name; // Имя пользователя

    @Email
    @NotBlank
    String email; // Почта пользователя

    @NotBlank
    String login; // Логин пользователя

    @PastOrPresent
    LocalDate birthday; // Дата рождения для пользователя
}
