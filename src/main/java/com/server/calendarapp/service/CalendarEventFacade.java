package com.server.calendarapp.service;

import com.google.api.services.calendar.model.Event;
import com.server.calendarapp.exception.EventsNotFoundException;
import com.server.calendarapp.payload.request.CalendarEventRequest;
import com.server.calendarapp.payload.response.CalendarEventsResponse;
import com.server.calendarapp.pojo.dbo.CalendarEvent;
import com.server.calendarapp.pojo.dto.EventDTO;
import com.server.calendarapp.pojo.mapper.CalendarEventMapper;
import com.server.calendarapp.pojo.mapper.GoogleEventMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

        List<CalendarEvent> customEvents = new LinkedList<>();
        List<Event> googleEvents = new LinkedList<>();

        try {
            customEvents = customEventService.getAllEvents(userID);
        } catch (EventsNotFoundException e) {
            e.printStackTrace();
        }

        try {
            googleEvents = googleEventsService.getGoogleEvents(userID);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<EventDTO> eventDTOS = CalendarEventMapper.mapCalendarEventListToDTO(customEvents);
        eventDTOS.addAll(GoogleEventMapper.mapGoogleEventsToDTO(googleEvents));

        Set<EventDTO> eventDTOSet = new HashSet<>(eventDTOS);

        return new CalendarEventsResponse(new Timestamp(new Date().getTime()), eventDTOSet);
    }


    public CalendarEvent createEvent(CalendarEventRequest event, String userID) {

        CalendarEvent calendarEvent = customEventService.createEvent(event, userID);
        try {
            googleEventsService.createEvent(calendarEvent, userID);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return calendarEvent;
    }

    public CalendarEvent updateEvent(CalendarEventRequest event, String userID) {

        CalendarEvent calendarEvent = customEventService.updateEvent(event, userID);
        try {
            googleEventsService.updateEvent(calendarEvent, userID);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return calendarEvent;
    }


    public void deleteEvent(String eventID, String userID) {

        customEventService.deleteEvent(eventID);
        try {
            googleEventsService.deleteEvent(eventID, userID);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
