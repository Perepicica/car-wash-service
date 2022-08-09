package ru.perepichka.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.perepichka.exception.*;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler({IdNotFoundException.class, NoOptionsForBookingException.class})
    public ResponseEntity<Object> handleIdNotFoundException(RuntimeException exc, WebRequest request) {
        return new ResponseEntity<>(getBody(exc), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoDataInDatabaseException.class)
    public ResponseEntity<Object> handleNoDataInDatabaseException(NoDataInDatabaseException exc, WebRequest request) {
        return new ResponseEntity<>(getBody(exc), HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler({
            EmailAlreadyExistsException.class,
            InvalidRoleTypeException.class,
            InvalidDateTimeException.class,
            InvalidCoefficientException.class,
            InvalidAppointmentStatusException.class,
            UpdateAppointmentException.class
    })
    public ResponseEntity<Object> handleBadArgumentException(RuntimeException exc, WebRequest request) {
        return new ResponseEntity<>(getBody(exc), HttpStatus.BAD_REQUEST);
    }

    private Map<String, Object> getBody(RuntimeException exc) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", exc.getMessage());
        return body;
    }

}
