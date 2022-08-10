package ru.perepichka.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class NoOptionsForBookingException extends RuntimeException {
    public NoOptionsForBookingException() {
        super("No options for your date and time, all boxes are busy");
    }
}
