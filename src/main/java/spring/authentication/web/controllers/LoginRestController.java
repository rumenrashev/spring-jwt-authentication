package spring.authentication.web.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import spring.authentication.service.LoginService;
import spring.authentication.web.models.JwtResponse;
import spring.authentication.web.models.LoginRequest;

import javax.validation.Valid;

import static spring.authentication.constants.GlobalConstants.*;

@RestController
@RequestMapping(LOGIN_URL)
@PreAuthorize("isAnonymous()")
public class LoginRestController {

    private final LoginService loginService;
    private final AuthenticationManager authenticationManager;

    /**
     * Constructs LoginController
     * @param loginService
     * @param authenticationManager
     * Authorizes dependencies via constructor-based injection
     */
    public LoginRestController(LoginService loginService, AuthenticationManager authenticationManager) {
        this.loginService = loginService;
        this.authenticationManager = authenticationManager;
    }

    /**
     * This method process HTTP post request.
     * @Valid annotation will check fields of loginRequest
     * and throws MethodArgumentNotValidException if there are errors.
     * @RequestBody annotation parses HTTP request body(JSON) to Java object.
     * @param loginRequest parsed Java object
     * calls login method from loginService
     * if login is successful
     * @return ResponseEntity with status code 200(OK)
     * and body jwtResponse serialized to JSON.
     */
    @PostMapping
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        JwtResponse jwtResponse = this.loginService.login(loginRequest, authenticationManager);
        return ResponseEntity
                .ok()
                .body(jwtResponse);
    }
}
