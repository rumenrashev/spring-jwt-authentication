package spring.authentication.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import spring.authentication.service.LoginService;
import spring.authentication.service.RegisterService;
import spring.authentication.service.models.UserDetailsServiceModel;
import spring.authentication.web.models.JwtResponse;
import spring.authentication.web.models.LoginRequest;
import spring.authentication.web.models.MessageResponse;
import spring.authentication.web.models.RegisterRequest;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@PreAuthorize("isAnonymous()")
public class AuthenticationController {

    private final ModelMapper modelMapper;
    private final RegisterService registerService;
    private final LoginService loginService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationController(ModelMapper modelMapper,
                                    RegisterService registerService,
                                    LoginService loginService,
                                    AuthenticationManager authenticationManager) {
        this.modelMapper = modelMapper;
        this.registerService = registerService;
        this.loginService = loginService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public ResponseEntity<MessageResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
        UserDetailsServiceModel serviceModel =
                this.modelMapper.map(registerRequest, UserDetailsServiceModel.class);
        this.registerService.registerUser(serviceModel);
        return ResponseEntity
                .ok(new MessageResponse("Successful registration"));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest loginRequest){
        JwtResponse jwtResponse = this.loginService.login(loginRequest, authenticationManager);
        return ResponseEntity
                .ok()
                .body(jwtResponse);
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
