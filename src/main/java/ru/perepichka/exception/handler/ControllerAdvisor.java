package ru.perepichka.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.perepichka.exception.EmailAlreadyExistsException;
import ru.perepichka.exception.IdNotFoundException;
import ru.perepichka.exception.InvalidRoleTypeException;
import ru.perepichka.exception.NoDataInDatabaseException;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(IdNotFoundException.class)
    public ResponseEntity<Object> handleIdNotFoundException(IdNotFoundException exc, WebRequest request) {
        return new ResponseEntity<>(getBody(exc), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoDataInDatabaseException.class)
    public ResponseEntity<Object> handleNoDataInDatabaseException(NoDataInDatabaseException exc, WebRequest request) {
        return new ResponseEntity<>(getBody(exc), HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler({EmailAlreadyExistsException.class, InvalidRoleTypeException.class})
    public ResponseEntity<Object> handleBadArgument(RuntimeException exc, WebRequest request) {
        return new ResponseEntity<>(getBody(exc), HttpStatus.BAD_REQUEST);
    }

    private Map<String, Object> getBody(RuntimeException exc) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", exc.getMessage());
        return body;
    }

}
