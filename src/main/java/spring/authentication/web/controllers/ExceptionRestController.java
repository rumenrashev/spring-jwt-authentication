package spring.authentication.web.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import spring.authentication.exception.EmailExistsException;
import spring.authentication.exception.UsernameExistsException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionRestController {

    /**
     * This handles MethodArgumentNotValidException.
     * Create a map with key value pair (filed name - error message).
     *
     * @return ResponseEntity with status code 400 - Bad request
     * and body map of errors serialized to JSON.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions
    (MethodArgumentNotValidException ex) {

        Map<String, String> fieldsErrors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            fieldsErrors.put(fieldName, errorMessage);
        });
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(fieldsErrors);
    }

    /**
     * This handles UsernameExistsException and EmailExistsException.
     * Create a map with key value pair (filed name - error message).
     *
     * @return ResponseEntity with status code 409 - Conflict
     * and body map of errors serialized to JSON.
     */
    @ExceptionHandler(value = {UsernameExistsException.class,
            EmailExistsException.class})
    public ResponseEntity<Map<String, String>> conflict(Exception exception) {
        Map<String, String> fieldsErrors = new HashMap<>();
        String exceptionName = exception.getClass().getSimpleName();
        switch (exceptionName) {
            case "UsernameExistsException":
                fieldsErrors.put("username", exception.getMessage());
                break;
            case "EmailExistsException":
                fieldsErrors.put("email", exception.getMessage());
                break;
        }
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(fieldsErrors);
    }
}
