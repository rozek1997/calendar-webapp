package com.server.calendarapp.pojo.mapper;

import com.server.calendarapp.algo.UUIDGenerator;
import com.server.calendarapp.payload.request.CalendarEventRequest;
import com.server.calendarapp.pojo.dbo.CalendarEvent;
import com.server.calendarapp.pojo.dto.EventDTO;

import java.util.List;
import java.util.stream.Collectors;

public class CalendarEventMapper {

    public static CalendarEvent mapRequestToCalendarEvent(CalendarEventRequest request, String userID) {
        CalendarEvent calendarEvent = new CalendarEvent();

        if (request.getEventID() == null) {
            calendarEvent.setEventID(UUIDGenerator.generateUnique());
        } else {
            calendarEvent.setEventID(request.getEventID());
        }

        return calendarEvent
                .setUserID(userID)
                .setDescription(request.getDescription())
                .setEventName(request.getEventName())
                .setStartTime(request.getStartTime())
                .setEndTime(request.getEndTime());
    }

    public static List<EventDTO> mapCalendarEventListToDTO(List<CalendarEvent> calendarEvents) {

        List<EventDTO> eventDTOS = calendarEvents.stream().map(event -> {
            EventDTO newEvent = new EventDTO();
            newEvent.setDescription(event.getDescription())
                    .setStartTime(event.getStartTime())
                    .setEndTime(event.getEndTime())
                    .setEventID(event.getEventID())
                    .setEventName(event.getEventName());

            return newEvent;
        }).collect(Collectors.toList());

        return eventDTOS;
    }


}
