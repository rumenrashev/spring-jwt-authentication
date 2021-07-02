package spring.authentication.web.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import spring.authentication.service.LoginService;
import spring.authentication.web.models.JwtResponse;
import spring.authentication.web.models.LoginRequest;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static spring.authentication.constants.GlobalConstants.LOGIN_URL;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginRestControllerTests {

    @MockBean
    LoginService loginService;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    AuthenticationManager authenticationManager;
    String LOGIN_REQUEST_CONTENT = "{\"username\":\"username\",\"password\":\"password\"}";
    LoginRequest loginRequest = new LoginRequest("username", "password");


    @Test
    @WithAnonymousUser
    void login_ShouldReturnStatus200() throws Exception {

        when(loginService.login(loginRequest, authenticationManager))
                .thenReturn(new JwtResponse());

        this.mockMvc.perform(post(LOGIN_URL)
                .contentType(APPLICATION_JSON)
                .content(LOGIN_REQUEST_CONTENT)
                .accept(APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithMockUser
    void login_ShouldReturnStatus403() throws Exception {
        this.mockMvc.perform(post(LOGIN_URL)
                .contentType(APPLICATION_JSON)
                .content(LOGIN_REQUEST_CONTENT)
                .accept(APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }
}
