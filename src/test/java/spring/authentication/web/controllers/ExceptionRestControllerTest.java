package spring.authentication.web.controllers;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import spring.authentication.exception.UsernameExistsException;
import spring.authentication.service.RegisterService;
import spring.authentication.web.models.RegisterRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static spring.authentication.constants.GlobalConstants.REGISTER_URL;

@SpringBootTest
@AutoConfigureMockMvc
class ExceptionRestControllerTest {

    @MockBean
    RegisterService registerService;
    @Autowired
    MockMvc mockMvc;
    String REGISTER_REQUEST_CONTENT = "{\"username\":\"username\",\"email\":\"admin@email.com\",\"password\":\"passwrod\"}";


    @Test
    void handleValidationExceptions() throws Exception {
    }

}
