package spring.authentication.service;

import org.springframework.security.authentication.AuthenticationManager;
import spring.authentication.web.models.JwtResponse;
import spring.authentication.web.models.LoginRequest;

public interface LoginService {

    JwtResponse login(LoginRequest loginRequest, AuthenticationManager authenticationManager);

}
