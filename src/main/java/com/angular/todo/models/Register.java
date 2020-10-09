package com.angular.todo.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "user")
//used to solve sterilization issues
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Register {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

    private String username;
    private String password;
    private String email_add;

    public Register(){
    }

    public Register(String username, String password, String email_add) {
        this.username = username;
        this.password = password;
        this.email_add = email_add;
    }


    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail_add() {
        return email_add;
    }

    public void setEmail_add(String email_add) {
        this.email_add = email_add;
    }


}
