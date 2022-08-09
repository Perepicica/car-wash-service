package ru.perepichka.exception;

public class NoOptionsForBookingException extends RuntimeException {
    public NoOptionsForBookingException() {
        super("No options for your date and time, all boxes are busy");
    }
}
