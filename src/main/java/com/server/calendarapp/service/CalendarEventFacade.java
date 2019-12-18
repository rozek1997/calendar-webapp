package com.server.calendarapp.service;

import com.google.api.services.calendar.model.Event;
import com.server.calendarapp.exception.EventsNotFoundException;
import com.server.calendarapp.exception.ResourceNotFound;
import com.server.calendarapp.payload.request.CalendarEventRequest;
import com.server.calendarapp.payload.response.ApiResponse;
import com.server.calendarapp.payload.response.CalendarEventsResponse;
import com.server.calendarapp.pojo.dbo.CalendarEvent;
import com.server.calendarapp.pojo.dto.EventDTO;
import com.server.calendarapp.pojo.mapper.CalendarEventMapper;
import com.server.calendarapp.pojo.mapper.GoogleEventMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

@Service
public class CalendarEventFacade {


    private CalendarEventsService customEventService;
    private GoogleEventsService googleEventsService;

    @Autowired
    public CalendarEventFacade(CalendarEventsService customEventService, GoogleEventsService googleEventsService) {
        this.customEventService = customEventService;
        this.googleEventsService = googleEventsService;
    }

    public CalendarEventsResponse getAllEvents(String userID) {

        ApiResponse apiResponse = new ApiResponse();

        List<CalendarEvent> customEvents = new LinkedList<>();
        List<Event> googleEvents = new LinkedList<>();

        try {
            customEvents = customEventService.getAllEvents(userID);
        } catch (EventsNotFoundException e) {
            updateResponse(apiResponse, HttpStatus.PARTIAL_CONTENT, e);
        }

        try {
            googleEvents = googleEventsService.getGoogleEvents(userID);
        } catch (IOException e) {

            updateResponse(apiResponse, HttpStatus.PARTIAL_CONTENT, e);
        }

        List<EventDTO> eventDTOS = CalendarEventMapper.mapCalendarEventListToDTO(customEvents);
        eventDTOS.addAll(GoogleEventMapper.mapGoogleEventsToDTO(googleEvents));

        Set<EventDTO> eventDTOSet = new HashSet<>(eventDTOS);

        return new CalendarEventsResponse(apiResponse, new Timestamp(new Date().getTime()), eventDTOSet);
    }


    public ApiResponse createEvent(CalendarEventRequest event, String userID) {


        ApiResponse apiResponse = new ApiResponse();
        CalendarEvent calendarEvent = customEventService.createEvent(event, userID);
        try {
            googleEventsService.createEvent(calendarEvent, userID);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return apiResponse;
    }

    public ApiResponse updateEvent(CalendarEventRequest event, String userID) {

        ApiResponse apiResponse = new ApiResponse();

        CalendarEvent calendarEvent = null;
        try {
            calendarEvent = customEventService.updateEvent(event, userID);
        } catch (EventsNotFoundException e) {

            updateResponse(apiResponse, HttpStatus.NOT_FOUND, e);
        }
        try {
            googleEventsService.updateEvent(calendarEvent, userID);
        } catch (IOException e) {

            updateResponse(apiResponse, HttpStatus.PARTIAL_CONTENT, e);
        }

        return apiResponse;
    }


    public ApiResponse deleteEvent(String eventID, String userID) {


        ApiResponse apiResponse = new ApiResponse();

        try {
            customEventService.deleteEvent(eventID);
        } catch (EventsNotFoundException e) {
            updateResponse(apiResponse, HttpStatus.NOT_FOUND, e);
        }
        try {
            googleEventsService.deleteEvent(eventID, userID);
        } catch (IOException e) {
            updateResponse(apiResponse, HttpStatus.NOT_FOUND, e);
        }


        return apiResponse;
    }

    private List<CalendarEvent> getCustomEventsList() {

        return null;

    }

    private void updateResponse(ApiResponse apiResponse, HttpStatus httpStatus, Exception e) {

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
