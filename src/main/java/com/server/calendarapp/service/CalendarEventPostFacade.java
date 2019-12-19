package com.server.calendarapp.service;

import com.server.calendarapp.payload.request.CalendarEventRequest;
import com.server.calendarapp.payload.response.ApiResponse;
import com.server.calendarapp.pojo.dbo.CalendarEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CalendarEventPostFacade {


    private CalendarEventsService customEventService;
    private GoogleEventsService googleEventsService;


    @Autowired
    public CalendarEventPostFacade(CalendarEventsService customEventService, GoogleEventsService googleEventsService) {
        this.customEventService = customEventService;
        this.googleEventsService = googleEventsService;
    }

    public ApiResponse createEvent(CalendarEventRequest event, String userID) {


        ApiResponse apiResponse = new ApiResponse(HttpStatus.CREATED, "");
        CalendarEvent calendarEvent = customEventService.createEvent(event, userID);
        try {
            googleEventsService.createEvent(calendarEvent, userID);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return apiResponse;
    }
}
