package com.server.calendarapp.pojo.mapper;

import com.server.calendarapp.payload.CalendarEventRequest;
import com.server.calendarapp.pojo.CalendarEvent;

public class CalendarEventMapper {

    public static CalendarEvent mapRequestToCalendarEvent(CalendarEventRequest request, String userID) {
        CalendarEvent calendarEvent = new CalendarEvent();

        if (request.getEventID() != null)
            calendarEvent.setEventID(request.getEventID());
        return calendarEvent
                .setUserID(userID)
                .setDescription(request.getDescription())
                .setEventName(request.getEventName())
                .setStartTime(request.getStartTime())
                .setEndTime(request.getEndTime());
    }


}
