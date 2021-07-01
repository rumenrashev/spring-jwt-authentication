package spring.authentication.web.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import spring.authentication.service.LoginService;
import spring.authentication.web.models.JwtResponse;
import spring.authentication.web.models.LoginRequest;

import javax.validation.Valid;

import static spring.authentication.web.constants.AuthenticationPaths.*;

@RestController
@RequestMapping(LOGIN_PATH)
@CrossOrigin(origins = CLIENT_ADDRESS, maxAge = 3600)
@PreAuthorize("isAnonymous()")
public class LoginController {

    private final LoginService loginService;
    private final AuthenticationManager authenticationManager;

    public LoginController(LoginService loginService, AuthenticationManager authenticationManager) {
        this.loginService = loginService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        JwtResponse jwtResponse = this.loginService.login(loginRequest, authenticationManager);
        return ResponseEntity
                .ok()
                .body(jwtResponse);
    }
}
