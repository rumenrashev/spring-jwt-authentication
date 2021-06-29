package spring.authentication.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import spring.authentication.data.entities.AuthorityEntity;
import spring.authentication.data.entities.AuthorityEnum;
import spring.authentication.data.entities.UserEntity;
import spring.authentication.data.repositories.UserRepository;
import spring.authentication.service.models.UserDetailsServiceModel;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserDetailsServiceImplTest {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @MockBean
    UserRepository userRepository;

    @Test
    void loadUserByUsername_shouldThrowException() {
        when(userRepository.getByUsername(any())).thenReturn(Optional.empty());
        assertThrows(
                UsernameNotFoundException.class,
                () -> userDetailsService.loadUserByUsername(any()));
    }

    @Test
    void loadUserByUsername_returnCorrectServiceModel() {
        final String id = "test_id";
        final String username = "username";
        final String password = "password";
        final UserDetailsServiceModel userDetailsServiceModel = new UserDetailsServiceModel();
        userDetailsServiceModel
                .setUsername(username)
                .setPassword(password);
        final AuthorityEntity authorityEntity = new AuthorityEntity().setAuthority(AuthorityEnum.ADMIN);
        final UserEntity userEntity = new UserEntity();
        userEntity
                .setUsername(username)
                .setPassword(password)
                .setAuthorities(Set.of(authorityEntity));
        when(userRepository.getByUsername(username)).thenReturn(Optional.of(userEntity));

        UserDetailsServiceModel expected = userDetailsService.loadUserByUsername(username);

        System.out.println(expected);

        assertEquals(username, expected.getUsername());
        assertEquals(password, expected.getPassword());
        assertEquals(1, expected.getAuthorities().size());
    }
}
