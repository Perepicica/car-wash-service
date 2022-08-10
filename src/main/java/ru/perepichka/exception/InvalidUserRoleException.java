package ru.perepichka.exception;

public class InvalidUserRoleException extends RuntimeException {
    public InvalidUserRoleException(String role) {
        super("Role " + role + " doesn't exist");
    }
}
