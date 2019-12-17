package com.server.calendarapp.controller;

import com.server.calendarapp.payload.CalendarEventRequest;
import com.server.calendarapp.pojo.CalendarEvent;
import com.server.calendarapp.security.CurrentUser;
import com.server.calendarapp.security.CustomerPrinciple;
import com.server.calendarapp.service.CalendarEventsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@CrossOrigin(origins = "*")
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
        System.out.println(currentUser.getEmail());
        return ResponseEntity.ok(calendarEventsService.getAllEvents(currentUser.getUserID()));
    }

    @PostMapping("createEvent")
    public ResponseEntity<?> createEvent(@Valid @RequestBody CalendarEventRequest calendarEventRequest
            , @CurrentUser CustomerPrinciple currentUser) {


        System.out.println(calendarEventRequest.getEventID());
        CalendarEvent event = calendarEventsService.createEvent(calendarEventRequest, currentUser.getUserID());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().buildAndExpand(event.getEventID()).toUri();
        return ResponseEntity.created(location).build();

    }

    @PutMapping("updateEvent")
    public ResponseEntity<?> updateEvent(@Valid @RequestBody CalendarEventRequest calendarEventRequest
            , @CurrentUser CustomerPrinciple currentUser) {


        CalendarEvent event = calendarEventsService.updateEvent(calendarEventRequest, currentUser.getUserID());
        return ResponseEntity.ok().build();

    }

    @DeleteMapping("deleteEvent/{eventID}")
    public ResponseEntity<?> updateEvent(@PathVariable("eventID") String eventID) {


        calendarEventsService.deleteEvent(eventID);
        return ResponseEntity.ok().build();

    }
}
