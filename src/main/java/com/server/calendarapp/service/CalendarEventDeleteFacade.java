package com.server.calendarapp.service;

import com.server.calendarapp.exception.EventsNotFoundException;
import com.server.calendarapp.payload.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CalendarEventDeleteFacade {

    private CalendarEventsService customEventService;
    private GoogleEventsService googleEventsService;

    @Autowired
    public CalendarEventDeleteFacade(CalendarEventsService customEventService, GoogleEventsService googleEventsService) {
        this.customEventService = customEventService;
        this.googleEventsService = googleEventsService;
    }

    public ApiResponse deleteEvent(String eventID, String userID) {

        ApiResponse apiResponse = new ApiResponse();

        deleteCustomEvent(apiResponse, userID, eventID);
        deleteGoogleEvent(apiResponse, userID, eventID);

        return apiResponse;
    }

    private void deleteCustomEvent(ApiResponse apiResponse, String userID, String eventID) {

        try {
            customEventService.deleteEvent(eventID);
        } catch (EventsNotFoundException e) {
            UpdateResponse.updateResponse(apiResponse, HttpStatus.NOT_FOUND, e);
        }


    }

    private void deleteGoogleEvent(ApiResponse apiResponse, String userID, String eventID) {
        try {
            googleEventsService.deleteEvent(eventID, userID);
        } catch (IOException e) {
            UpdateResponse.updateResponse(apiResponse, HttpStatus.NOT_FOUND, e);
        }

    }

}
