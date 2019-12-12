package com.server.calendarapp.service;

import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import com.server.calendarapp.payload.CalendarEventsResponse;
import com.server.calendarapp.pojo.CalendarEvent;
import com.server.calendarapp.repository.CalendarEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class CalendarEventsService {


    private CalendarEventRepository calendarEventRepository;

    @Autowired
    public CalendarEventsService(CalendarEventRepository calendarEventRepository) {
        this.calendarEventRepository = calendarEventRepository;
    }


    public CalendarEventsResponse getAllEvents(String userID) {

        List<CalendarEvent> calendarEventList = calendarEventRepository
                .findByUserID(userID)
                .orElseThrow(() -> new ResourceNotFoundException("Cant find events for user" + userID));


        return new CalendarEventsResponse(new Timestamp(new Date().getTime()), calendarEventList);

    }

}
