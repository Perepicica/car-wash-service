package ru.perepichka.exception;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String email) {
        super("User with such email: " + email + " already exists");
    }
}
