package ru.perepichka.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.perepichka.exception.*;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NoOptionsForBookingException.class)
    public ResponseEntity<Object> handleNoOptionsForBooking(NoOptionsForBookingException exc) {
        return new ResponseEntity<>(getBody(exc), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({
            IdNotFoundException.class,
            EmailAlreadyExistsException.class,
            InvalidUserRoleException.class,
            InvalidAppointmentStatusException.class,
            UpdateAppointmentException.class,
            OperatorToBoxAssigningException.class,
            DeleteBoxException.class
    })
    public ResponseEntity<Object> handleBadArgumentException(RuntimeException exc) {
        return new ResponseEntity<>(getBody(exc), HttpStatus.BAD_REQUEST);
    }

    private Map<String, Object> getBody(RuntimeException exc) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", exc.getMessage());
        return body;
    }

}
