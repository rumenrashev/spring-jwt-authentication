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
import spring.authentication.exception.EmailExistsException;
import spring.authentication.exception.UsernameExistsException;
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

    final String validUsername = "username";
    final String invalidUsername = "u";
    final String CONTENT_STRUCTURE = "{\"username\":\"%s\",\"email\":\"admin@email.com\",\"password\":\"password\"}";

    String VALID_REQUEST_CONTENT = String.format(CONTENT_STRUCTURE, validUsername);
    String INVALID_REQUEST_CONTENT = String.format(CONTENT_STRUCTURE, invalidUsername);

    @Test
    @WithMockUser
    void register_ShouldReturnStatusCode403Forbidden() throws Exception {
        this.mockMvc.perform(post(REGISTER_URL)
                .contentType(APPLICATION_JSON)
                .content(VALID_REQUEST_CONTENT)
                .accept(APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithAnonymousUser
    void register_ShouldReturnStatusCode200_Ok() throws Exception {
        when(registerService.registerUser(new RegisterRequest()))
                .thenReturn(Mockito.anyString());
        this.mockMvc.perform(post(REGISTER_URL)
                .contentType(APPLICATION_JSON)
                .content(VALID_REQUEST_CONTENT)
                .accept(APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithAnonymousUser
    void conflict_ShouldReturnStatusCode409ConflictBecauseUsernameIsBusy() throws Exception {
        Mockito
                .doThrow(new UsernameExistsException())
                .when(registerService).registerUser(Mockito.any());
        this.mockMvc.perform(post(REGISTER_URL)
                .contentType(APPLICATION_JSON)
                .content(VALID_REQUEST_CONTENT)
                .accept(APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    @WithAnonymousUser
    void conflict_ShouldReturnStatusCode409ConflictBecauseEmiIsBusy() throws Exception {
        Mockito
                .doThrow(new EmailExistsException())
                .when(registerService).registerUser(Mockito.any());
        this.mockMvc.perform(post(REGISTER_URL)
                .contentType(APPLICATION_JSON)
                .content(VALID_REQUEST_CONTENT)
                .accept(APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    @WithAnonymousUser
    void conflict_ShouldReturnStatusCode400BadRequestBecauseUsernameIsTooShort() throws Exception {
        this.mockMvc.perform(post(REGISTER_URL)
                .contentType(APPLICATION_JSON)
                .content(INVALID_REQUEST_CONTENT)
                .accept(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

}
