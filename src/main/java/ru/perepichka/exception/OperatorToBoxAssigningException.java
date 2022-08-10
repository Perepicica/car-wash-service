package ru.perepichka.exception;

public class OperatorToBoxAssigningException extends RuntimeException {
    public OperatorToBoxAssigningException(String reason) {
        super(reason);
    }
}
