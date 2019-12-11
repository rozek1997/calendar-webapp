package com.server.calendarapp.controller;


import com.server.calendarapp.model.CustomUserDetailService;
import com.server.calendarapp.model.JWTRequest;
import com.server.calendarapp.model.JWTResponse;
import com.server.calendarapp.security.JWTTokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
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

    @PostMapping("signin")
    public ResponseEntity<?> signIn(@RequestBody JWTRequest authRequest) throws Exception {

        authenticate(authRequest.getUsername(), authRequest.getPassword());


        final UserDetails userDetails = customUserDetailService.loadUserByUsername(authRequest.getUsername());

        final String token = jwtTokenGenerator.generateToken(userDetails);


        return ResponseEntity.ok(new JWTResponse(token));
    }


    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
