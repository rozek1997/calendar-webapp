package com.server.calendarapp.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class ApiResponse {

    private HttpStatus httpStatus;
    private String message;


    public ApiResponse() {
        httpStatus = HttpStatus.OK;
        message = "";
    }
}
