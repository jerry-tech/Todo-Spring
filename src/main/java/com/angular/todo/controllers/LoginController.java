package com.angular.todo.controllers;

import com.angular.todo.JWT.JsonWebToken;
import com.angular.todo.models.AuthenticationRequest;
import com.angular.todo.models.Register;
import com.angular.todo.repositories.LoginRepository;
import com.angular.todo.services.MyUserDetailsService;
import com.angular.todo.services.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginController {

    @Autowired//MyUserDetails service
    private MyUserDetailsService userDetailsService;

    @Autowired//Json web token
    private JsonWebToken jsonWebToken;

    @Autowired//loginRepository interface
    private LoginRepository loginRepository;

    @Autowired//Autowiring the response handler for the entity response handler
    ResponseHandler responseHandler;

    //declaring the variable for the assigning the JWT value.
    String JWT = null;


    @RequestMapping(value = "/api/v1/login", method = RequestMethod.POST)//@PostMapping
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

        //loading user credentials by users inputted username
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        //using method in the login repository to get other users credentials
        Register register = loginRepository.getUserByUsername(authenticationRequest.getUsername());

        //instantiating the BCryptPasswordEncoder class
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        //comparing the user inputted password and the hashed password in the db
        boolean isMatches = passwordEncoder.matches(authenticationRequest.getPassword(), userDetails.getPassword());

        if (isMatches && userDetails.getUsername().equals(authenticationRequest.getUsername())) {
            //using the userDetails to generate the JSon Web Token
            JWT = jsonWebToken.generateToken(userDetails);

            Map<String, Object> response = new HashMap<>();
            response.put("user_id", register.getUser_id());
            response.put("JWT", JWT);
            response.put("username", userDetails.getUsername());
            response.put("email_Address", register.getEmail_add());


            //building custom success response
            return responseHandler.generateResponse(HttpStatus.OK, true, "Login Successfully", response);
        } else {
            //building custom error response
            return responseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, "Invalid Username or Password", new HashMap<>());
        }

    }

}