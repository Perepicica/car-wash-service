package ru.perepichka.exception;

public class OperatorAssigningException extends RuntimeException {
    public OperatorAssigningException(String reason) {
        super(reason);
    }
}
