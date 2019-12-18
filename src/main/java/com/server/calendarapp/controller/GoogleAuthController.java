package com.server.calendarapp.controller;

import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.server.calendarapp.payload.ApiResponse;
import com.server.calendarapp.payload.GoogleLoginResponse;
import com.server.calendarapp.security.CurrentUser;
import com.server.calendarapp.security.CustomerPrinciple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/auth/google")
public class GoogleAuthController {

    @Qualifier("myFlow")
    private GoogleAuthorizationCodeFlow googleAuthorizationCodeFlow;

    @Qualifier("myAuthCodeRequestUrl")
    private AuthorizationCodeRequestUrl googleAuthorizationCodeRequestUrl;

    @Value("${google.redirectURI}")
    private String redirectURI;

    @Autowired
    public GoogleAuthController(GoogleAuthorizationCodeFlow googleAuthorizationCodeFlow
            , AuthorizationCodeRequestUrl googleAuthorizationCodeRequestUrl) {
        this.googleAuthorizationCodeFlow = googleAuthorizationCodeFlow;
        this.googleAuthorizationCodeRequestUrl = googleAuthorizationCodeRequestUrl;
    }

    @GetMapping("/login")
    public ResponseEntity<?> googleLogin(HttpServletResponse response, @CurrentUser CustomerPrinciple currentUser) throws IOException {
        System.out.println(googleAuthorizationCodeRequestUrl.setState(currentUser.getUserID()).build());
        return ResponseEntity.ok().body(new GoogleLoginResponse(googleAuthorizationCodeRequestUrl.setState(currentUser.getUserID()).build()));

    }


    @GetMapping("/login/callback")
    public ResponseEntity<?> googleLoginCallback(@RequestParam(value = "code") String code,
                                                 @RequestParam(value = "state") String state) throws IOException {

        TokenResponse response = googleAuthorizationCodeFlow.newTokenRequest(code)
                .setRedirectUri(redirectURI)
                .execute();
        googleAuthorizationCodeFlow.createAndStoreCredential(response, state);

        return ResponseEntity.ok().body(new ApiResponse(true, "You have succesfully logged in"));
    }


}
