package ru.perepichka.exception;

public class InvalidRoleTypeException extends RuntimeException {
    public InvalidRoleTypeException(String role) {
        super("Role " + role + " doesn't exist");
    }
}
