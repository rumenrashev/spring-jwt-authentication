package spring.authentication.web.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import spring.authentication.exception.EmailExistsException;
import spring.authentication.exception.UsernameExistsException;
import spring.authentication.service.RegisterService;
import spring.authentication.web.models.MessageResponse;
import spring.authentication.web.models.RegisterRequest;

import javax.validation.Valid;

import java.util.HashMap;
import java.util.Map;

import static spring.authentication.constants.GlobalConstants.*;

@RestController
@RequestMapping(REGISTER_URL)
@PreAuthorize("isAnonymous()")
public class RegisterRestController {

    private final RegisterService registerService;

    /**
     * Constructs RegisterController
     * @param registerService
     * Authorizes dependency via constructor-based injection
     */
    public RegisterRestController(RegisterService registerService) {
        this.registerService = registerService;
    }

    /**
     * This method process HTTP post request.
     * @Valid annotation will check fields of registerRequest
     * and throws MethodArgumentNotValidException if there are errors.
     * @RequestBody annotation parses HTTP request body(JSON) to Java object.
     * @param registerRequest parsed Java object
     * calls registerUser method from registerService
     * if registration is successful
     * @return ResponseEntity with status code 200(OK)
     * and body MessageResponse serialized to JSON.
     */
    @PostMapping
    public ResponseEntity<MessageResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
        String username = this.registerService.registerUser(registerRequest);
        return ResponseEntity
                .ok()
                .body(new MessageResponse(String.format(SUCCESSFUL_REGISTRATION_MESSAGE,username)));
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
}
