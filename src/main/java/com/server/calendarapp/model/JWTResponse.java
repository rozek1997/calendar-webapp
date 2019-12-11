package com.server.calendarapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
public class JWTResponse implements Serializable {

    private final String jwtToken;

}
