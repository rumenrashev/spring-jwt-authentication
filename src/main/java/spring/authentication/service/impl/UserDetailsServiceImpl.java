package spring.authentication.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import spring.authentication.data.repositories.UserRepository;
import spring.authentication.service.LoginService;
import spring.authentication.service.models.GrantedAuthorityServiceModel;
import spring.authentication.service.models.UserDetailsServiceModel;
import spring.authentication.utils.JwtUtils;
import spring.authentication.web.models.JwtResponse;
import spring.authentication.web.models.LoginRequest;

import java.util.Set;
import java.util.stream.Collectors;

import static spring.authentication.exception.ExceptionMessages.USERNAME_NOT_FOUND_EXCEPTION_MESSAGE;

@Service
public class UserDetailsServiceImpl implements UserDetailsService, LoginService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final JwtUtils jwtUtils;

    public UserDetailsServiceImpl(UserRepository userRepository, ModelMapper modelMapper, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public UserDetailsServiceModel loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.getByUsername(username)
                .map(entity-> this.modelMapper.map(entity, UserDetailsServiceModel.class))
                .orElseThrow(()-> new UsernameNotFoundException(USERNAME_NOT_FOUND_EXCEPTION_MESSAGE));
    }

    @Override
    public JwtResponse login(LoginRequest loginRequest, AuthenticationManager authenticationManager) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsServiceModel userDetails = (UserDetailsServiceModel) authentication.getPrincipal();
        String token = jwtUtils.generateJwtToken(userDetails);
        JwtResponse jwt = this.modelMapper.map(userDetails, JwtResponse.class);
        Set<String> roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthorityServiceModel::getAuthority)
                .collect(Collectors.toSet());
        return jwt
                .setToken(token)
                .setRoles(roles);
    }


}
