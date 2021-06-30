package spring.authentication.web.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import spring.authentication.exception.EmailExistsException;
import spring.authentication.exception.UsernameExistsException;
import spring.authentication.web.models.ErrorResponse;

@ControllerAdvice
public class GlobalErrorHandleController {

    @ExceptionHandler(value = {UsernameExistsException.class,
            EmailExistsException.class})
    public ResponseEntity<ErrorResponse> conflict(Exception exception) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(exception.getMessage()));
    }
}
