package spring.authentication.web.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import spring.authentication.service.RegisterService;
import spring.authentication.web.models.MessageResponse;
import spring.authentication.web.models.RegisterRequest;

import javax.validation.Valid;

import static spring.authentication.web.constants.AuthenticationPaths.CLIENT_ADDRESS;
import static spring.authentication.web.constants.AuthenticationPaths.REGISTER_PATH;

@CrossOrigin(origins = CLIENT_ADDRESS, maxAge = 3600)
@RestController
@RequestMapping(REGISTER_PATH)
@PreAuthorize("isAnonymous()")
public class RegisterController {

    private final RegisterService registerService;

    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @PostMapping
    public ResponseEntity<MessageResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
        this.registerService.registerUser(registerRequest);
        return ResponseEntity
                .ok()
                .body(new MessageResponse("Successful registration"));
    }
}
