package com.angular.todo;

import com.angular.todo.JWT.JsonWebToken;
import com.angular.todo.configurations.PassEncoder;
import com.angular.todo.controllers.RegisterController;
import com.angular.todo.repositories.RegisterRepository;
import com.angular.todo.services.MyUserDetailsService;
import com.angular.todo.services.ResponseHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@WebMvcTest(RegisterController.class)
public class RegisterControllerTest {

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

//    @Test
//    public void getSingleUser() throws Exception {
//        mockMvc.perform(post("/api/v1/register").contentType(MediaType.APPLICATION_JSON))
//                .andExpect(header().string("Location","/api/v1/register/2"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(2))
//                .andExpect(content().json("{}"));
//
//
//    }



}
