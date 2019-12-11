package com.server.calendarapp.pojo;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;


public class UserPrinciple implements UserDetails {

    private String username;
    private String email;
    private String password;


    public UserPrinciple(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public static UserPrinciple build(User user) {


        return new UserPrinciple(
                user.getUsername(),
                user.getEmail(),
                user.getPassword()
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
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
