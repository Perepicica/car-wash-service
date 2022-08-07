package ru.perepichka.exception;

public class NoDataInDatabaseException extends RuntimeException {
    public NoDataInDatabaseException() {
        super("No data found");
    }
}
