package spring.authentication.web.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import spring.authentication.service.RegisterService;
import spring.authentication.web.models.MessageResponse;
import spring.authentication.web.models.RegisterRequest;

import javax.validation.Valid;

import static spring.authentication.constants.GlobalConstants.*;

@CrossOrigin(origins = CLIENT_ADDRESS, maxAge = 3600)
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
}
