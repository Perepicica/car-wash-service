package ru.perepichka.exception;

public class DeleteBoxException extends RuntimeException {
    public DeleteBoxException(String reason) {
        super(reason);
    }
}
