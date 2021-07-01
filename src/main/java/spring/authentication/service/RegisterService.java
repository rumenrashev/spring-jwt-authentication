package spring.authentication.service;

import spring.authentication.service.models.UserDetailsServiceModel;
import spring.authentication.web.models.RegisterRequest;

public interface RegisterService {

    String registerUser(RegisterRequest registerRequest);
}
