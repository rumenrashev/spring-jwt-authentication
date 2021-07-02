package spring.authentication.web.controllers;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import spring.authentication.service.RegisterService;
import spring.authentication.web.models.RegisterRequest;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static spring.authentication.constants.GlobalConstants.REGISTER_URL;

@SpringBootTest
@AutoConfigureMockMvc
class RegisterRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    RegisterService registerService;

    String REGISTER_REQUEST_CONTENT = "{\"username\":\"username\",\"email\":\"admin@email.com\",\"password\":\"passwrod\"}";

    @Test
    @WithMockUser
    void register_ShouldReturnStatusCode403() throws Exception {
        this.mockMvc.perform(post(REGISTER_URL)
                .contentType(APPLICATION_JSON)
                .content(REGISTER_REQUEST_CONTENT)
                .accept(APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }
    @Test
    @WithAnonymousUser
    void register_ShouldReturnStatusCode200() throws Exception {
        when(registerService.registerUser(new RegisterRequest()))
                .thenReturn("username");
        this.mockMvc.perform(post(REGISTER_URL)
                .contentType(APPLICATION_JSON)
                .content(REGISTER_REQUEST_CONTENT)
                .accept(APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }
}
