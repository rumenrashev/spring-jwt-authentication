package spring.authentication.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import spring.authentication.exception.EmailExistsException;
import spring.authentication.exception.ExceptionMessages;
import spring.authentication.exception.UsernameExistsException;
import spring.authentication.service.RegisterService;
import spring.authentication.service.models.UserDetailsServiceModel;
import spring.authentication.web.models.MessageResponse;
import spring.authentication.web.models.RegisterRequest;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@PreAuthorize("isAnonymous()")
public class AuthenticationController {

    private final ModelMapper modelMapper;
    private final RegisterService registerService;

    public AuthenticationController(ModelMapper modelMapper, RegisterService registerService) {
        this.modelMapper = modelMapper;
        this.registerService = registerService;
    }

    @PostMapping("/register")
    public ResponseEntity<MessageResponse> register(@RequestBody RegisterRequest registerRequest){
        UserDetailsServiceModel serviceModel =
                this.modelMapper.map(registerRequest, UserDetailsServiceModel.class);
            this.registerService.registerUser(serviceModel);
            return ResponseEntity.ok(new MessageResponse("Successful registration"));
    }
}
