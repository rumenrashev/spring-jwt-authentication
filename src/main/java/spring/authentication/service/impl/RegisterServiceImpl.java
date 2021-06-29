package spring.authentication.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
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

@Service
public class RegisterServiceImpl implements RegisterService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;

    public RegisterServiceImpl(ModelMapper modelMapper,
                               UserRepository userRepository,
                               AuthorityRepository authorityRepository) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
    }

    @Override
    public UserDetailsServiceModel registerUser(UserDetailsServiceModel serviceModel) {
        if(this.userRepository.existsByUsername(serviceModel.getUsername())){
            throw new UsernameExistsException();
        }
        if(this.userRepository.existsByEmail(serviceModel.getEmail())){
            throw new EmailExistsException();
        }
        UserEntity userEntity = this.modelMapper.map(serviceModel, UserEntity.class);
        serviceModel
                .getAuthorities()
                .stream()
                .map(this::getAuthorityEntityByString)
                .forEach(a-> userEntity.getAuthorities().add(a));
        UserEntity savedEntity = this.userRepository.save(userEntity);
        return this.modelMapper.map(savedEntity,UserDetailsServiceModel.class);
    }

    private AuthorityEntity getAuthorityEntityByString(GrantedAuthority grantedAuthority){
        try {
            String name = grantedAuthority.getAuthority();
            return  this.authorityRepository
                    .getByAuthority(AuthorityEnum.valueOf(name))
                    .get();
        }catch (Exception exception){
            throw new AuthorityNotExistsException();
        }
    }
}
