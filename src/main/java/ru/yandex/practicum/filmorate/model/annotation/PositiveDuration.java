package ru.yandex.practicum.filmorate.model.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PositiveDurationValidator.class)
public @interface PositiveDuration {
    String message() default "Продолжительность должна быть положительным числом";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}