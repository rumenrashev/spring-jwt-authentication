package spring.authentication.web.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import spring.authentication.exception.EmailExistsException;
import spring.authentication.exception.UsernameExistsException;
import spring.authentication.web.models.ErrorResponse;
import spring.authentication.web.models.MessageResponse;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalErrorHandleController {

    @ExceptionHandler(value = {UsernameExistsException.class,
                               EmailExistsException.class})
    public ResponseEntity<ErrorResponse> conflict(Exception exception){
        return ResponseEntity
                .badRequest()
                .body(new ErrorResponse(exception.getMessage()));
    }
}
