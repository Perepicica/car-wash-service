package ru.perepichka.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("Пользователь с данным email не найден");
    }
}
