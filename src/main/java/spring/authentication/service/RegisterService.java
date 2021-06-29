package spring.authentication.service;

import spring.authentication.service.models.UserDetailsServiceModel;

public interface RegisterService {

    UserDetailsServiceModel registerUser(UserDetailsServiceModel serviceModel);
}
