package com.angular.todo;

import com.angular.todo.JWT.JsonWebToken;
import com.angular.todo.configurations.PassEncoder;
import com.angular.todo.controllers.RegisterController;
import com.angular.todo.models.Register;
import com.angular.todo.repositories.RegisterRepository;
import com.angular.todo.services.MyUserDetailsService;
import com.angular.todo.services.ResponseHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.annotation.PathVariable;


import javax.swing.text.html.Option;
import java.util.Optional;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@WebMvcTest(RegisterController.class)
public class RegisterControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    public PassEncoder passwordEncoder;

    @MockBean
    public MyUserDetailsService myUserDetailsService;

    @MockBean
    public JsonWebToken jsonWebToken;

    @MockBean
    public ResponseHandler responseHandler;

    @MockBean
    private RegisterRepository registerRepository;

    @Test
    public void getAllRegisteredUser() throws Exception {
        mockMvc.perform(get("/api/v1/register"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[]"));

        verify(registerRepository, times(1)).findAll();
    }

    @Test
    public void createUser() throws Exception{

        given(registerRepository.saveAndFlush(any(Register.class))).willAnswer((invocation) -> invocation.getArgument(0));


        final Register register = new Register("jerry", "123456", "ujeremiah200@gmail.com");

        String username = register.getUsername();
        String password = passwordEncoder.encrytePassword(register.getPassword());
        String email_add = register.getEmail_add();

        Register addUser = new Register(username, password, email_add);

        mockMvc.perform(post("/api/v1/register")
        .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(addUser)))
                .andExpect(status().isOk());

    }



}
