package com.angular.todo.Logic;


import com.angular.todo.models.Register;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.ArrayList;
import java.util.Collection;

public class MyUserDetails implements UserDetails {

    private Register register;


    public MyUserDetails(Register register) {
        this.register = register;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @Override
    public String getPassword() {
        return register.getPassword();
    }

    @Override
    public String getUsername() {
        return register.getUsername();
    }

    public Long userId(){
        return register.getUser_id();
    }

    public String getEmailAddress(){
        return register.getEmail_add();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
