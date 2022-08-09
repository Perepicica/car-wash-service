package ru.perepichka.exception;

public class InvalidDateTimeException extends RuntimeException {
    public InvalidDateTimeException(String format) {
        super("Invalid date/time, follow time format " + format);
    }
}
