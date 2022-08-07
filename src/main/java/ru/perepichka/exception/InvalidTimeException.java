package ru.perepichka.exception;

public class InvalidTimeException extends RuntimeException {
    public InvalidTimeException() {
        super("Invalid time, follow time format HH:mm");
    }
}
