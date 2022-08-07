package ru.perepichka.exception;

public class InvalidCoefficientException extends RuntimeException {
    public InvalidCoefficientException() {
        super("Use integers only and dot(.) as delimiter");
    }
}
