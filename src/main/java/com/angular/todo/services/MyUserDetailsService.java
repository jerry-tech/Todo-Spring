package com.angular.todo.services;

import com.angular.todo.Logic.MyUserDetails;
import com.angular.todo.models.Register;
import com.angular.todo.repositories.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired//autowiring the login repository so as to get the method in the interface
    private LoginRepository loginRepository;

    @Override//overriding the loadUserByUsername method
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Register register = loginRepository.getUserByUsername(username);

        //checking if the record is null
        if(register == null){
            throw new UsernameNotFoundException("Username not found");
        }
        //passing the register object to the constructor of the MyUserDetails class
        return new MyUserDetails(register);
    }
}
