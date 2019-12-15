package com.server.calendarapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class GoogleLoginException extends IOException {


    public GoogleLoginException(String message) {
        super(message);
    }
}
