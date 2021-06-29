package spring.authentication.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import spring.authentication.data.entities.AuthorityEntity;
import spring.authentication.data.entities.AuthorityEnum;
import spring.authentication.data.entities.UserEntity;
import spring.authentication.data.repositories.AuthorityRepository;
import spring.authentication.data.repositories.UserRepository;
import spring.authentication.exception.AuthorityNotExistsException;
import spring.authentication.exception.EmailExistsException;
import spring.authentication.exception.UsernameExistsException;
import spring.authentication.service.RegisterService;
import spring.authentication.service.models.UserDetailsServiceModel;

import javax.transaction.Transactional;
import java.util.Set;

@Service
public class RegisterServiceImpl implements RegisterService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterServiceImpl(ModelMapper modelMapper,
                               UserRepository userRepository,
                               AuthorityRepository authorityRepository,
                               PasswordEncoder passwordEncoder) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public UserDetailsServiceModel registerUser(UserDetailsServiceModel serviceModel) {
        if(this.userRepository.existsByUsername(serviceModel.getUsername())){
            throw new UsernameExistsException();
        }
        if(this.userRepository.existsByEmail(serviceModel.getEmail())){
            throw new EmailExistsException();
        }
        UserEntity userEntity = this.modelMapper.map(serviceModel, UserEntity.class);
        userEntity.setPassword(this.passwordEncoder.encode(userEntity.getPassword()));
        AuthorityEntity userAuthority = this.authorityRepository
                .getByAuthority(AuthorityEnum.USER)
                .orElseThrow(AuthorityNotExistsException::new);
        userEntity.setAuthorities(Set.of(userAuthority));
        UserEntity savedEntity = this.userRepository.save(userEntity);
        return this.modelMapper.map(savedEntity,UserDetailsServiceModel.class);
    }
}
