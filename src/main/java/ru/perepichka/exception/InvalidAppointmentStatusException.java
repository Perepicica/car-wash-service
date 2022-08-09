package ru.perepichka.exception;

public class InvalidAppointmentStatusException extends RuntimeException {
    public InvalidAppointmentStatusException(String status) {
        super("Appointment " + status + " doesn't exist");
    }
}
