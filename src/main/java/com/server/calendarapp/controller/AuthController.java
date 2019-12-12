package com.server.calendarapp.controller;


import com.server.calendarapp.payload.JWTResponse;
import com.server.calendarapp.payload.LoginRequest;
import com.server.calendarapp.security.JWTTokenGenerator;
import com.server.calendarapp.service.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    private AuthenticationManager authenticationManager;
    private JWTTokenGenerator jwtTokenGenerator;
    private CustomUserDetailService customUserDetailService;


    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JWTTokenGenerator jwtTokenGenerator, CustomUserDetailService customUserDetailService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenGenerator = jwtTokenGenerator;
        this.customUserDetailService = customUserDetailService;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtTokenGenerator.generateToken(authentication);
        return ResponseEntity.ok(new JWTResponse(jwt));
    }


}
