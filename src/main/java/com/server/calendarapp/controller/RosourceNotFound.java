package com.server.calendarapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RosourceNotFound extends RuntimeException {

    public RosourceNotFound(String message) {
        super(message);
    }
}
