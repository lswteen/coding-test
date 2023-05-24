package com.illuminarean.example.error;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Collectors;


@ControllerAdvice
public class GeneralExceptionHandler {

    @ExceptionHandler({
            MethodArgumentNotValidException.class
    })
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<>(
                e.getBindingResult().getFieldErrors().stream().map(NotValidError::new).collect(Collectors.toList()),
                headers,
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler({
            AlreadyExistsException.class,
            NotFoundException.class,
    })
    public ResponseEntity<?> handle(RuntimeException e) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<>(
                e.getMessage(),
                headers,
                HttpStatus.BAD_REQUEST
        );
    }

    public static class NotValidError {
        private final String message;
        private final String field;

        public String getMessage() {
            return message;
        }

        public String getField() {
            return field;
        }

        NotValidError(Throwable throwable, String field) {
            this(throwable.getMessage(), field);
        }

        NotValidError(String message, String field) {
            this.message = message;
            this.field = field;
        }

        public NotValidError(FieldError fieldError) {
            this(fieldError.getDefaultMessage(), fieldError.getField());
        }
    }
}
