package com.server.calendarapp.controller;

import com.server.calendarapp.security.CurrentUser;
import com.server.calendarapp.security.CustomerPrinciple;
import com.server.calendarapp.service.CalendarEventsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/calendarevents")
public class CalendarEventController {

    private CalendarEventsService calendarEventsService;

    @Autowired
    public CalendarEventController(CalendarEventsService calendarEventsService) {
        this.calendarEventsService = calendarEventsService;
    }

    @GetMapping
    public ResponseEntity<?> getAllUsersEvent(@CurrentUser CustomerPrinciple currentUser) {

        return ResponseEntity.ok(calendarEventsService.getAllEvents(currentUser.getUserID()));
    }
}
