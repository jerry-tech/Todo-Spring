package com.angular.todo.models;

public class AuthenticationResponse {

    private final String JWT;

    public AuthenticationResponse(String jwt) {
        this.JWT = jwt;
    }

    public String getJWT() {
        return JWT;
    }
}
