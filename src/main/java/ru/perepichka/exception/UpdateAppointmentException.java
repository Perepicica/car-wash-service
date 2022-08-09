package ru.perepichka.exception;

public class UpdateAppointmentException extends RuntimeException {
    public UpdateAppointmentException() {
        super("Update or status either date/time/service");
    }
}
