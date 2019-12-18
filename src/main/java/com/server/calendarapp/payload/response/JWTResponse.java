package com.server.calendarapp.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
public class JWTResponse implements Serializable {

    private final String tokenType = "Bearer";
    private String accessToken;


}
