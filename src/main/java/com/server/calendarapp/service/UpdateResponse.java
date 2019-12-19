package com.server.calendarapp.service;

import com.server.calendarapp.exception.ResourceNotFound;
import com.server.calendarapp.payload.response.ApiResponse;
import org.springframework.http.HttpStatus;

public class UpdateResponse {


    public static void updateResponse(ApiResponse apiResponse, HttpStatus httpStatus, Exception e) {

        HttpStatus status = apiResponse.getHttpStatus();
        if (status == HttpStatus.OK) {
            apiResponse.setHttpStatus(httpStatus);
            apiResponse.setMessage(e.getMessage() + " ");
        }

        if (status == HttpStatus.NOT_FOUND || status == HttpStatus.PARTIAL_CONTENT) {
            apiResponse.setMessage(apiResponse.getMessage() + "+ " + e.getMessage());
            throw new ResourceNotFound(apiResponse.getMessage());
        }

    }
}
