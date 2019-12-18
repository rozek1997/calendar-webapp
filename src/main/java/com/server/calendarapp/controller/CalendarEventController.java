package com.server.calendarapp.controller;

import com.server.calendarapp.exception.EventsNotFoundException;
import com.server.calendarapp.payload.request.CalendarEventRequest;
import com.server.calendarapp.payload.response.ApiResponse;
import com.server.calendarapp.security.CurrentUser;
import com.server.calendarapp.security.CustomerPrinciple;
import com.server.calendarapp.service.CalendarEventFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;

@RestController
@RequestMapping("/api/calendarevents")
public class CalendarEventController {

    private CalendarEventFacade calendarEventFacade;

    @Autowired
    public CalendarEventController(CalendarEventFacade calendarEventFacade) {
        this.calendarEventFacade = calendarEventFacade;
    }

    @GetMapping
    public ResponseEntity<?> getAllUsersEvent(@CurrentUser CustomerPrinciple currentUser) throws EventsNotFoundException {
        return ResponseEntity.ok(calendarEventFacade.getAllEvents(currentUser.getUserID()));
    }

    @PostMapping("createEvent")
    public ResponseEntity<?> createEvent(@Valid @RequestBody CalendarEventRequest calendarEventRequest
            , @CurrentUser CustomerPrinciple currentUser) {

        ApiResponse apiResponse = calendarEventFacade.createEvent(calendarEventRequest, currentUser.getUserID());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().buildAndExpand().toUri();
        return ResponseEntity.created(location).body(apiResponse);

    }

    @PutMapping("updateEvent")
    public ResponseEntity<?> updateEvent(@Valid @RequestBody CalendarEventRequest calendarEventRequest
            , @CurrentUser CustomerPrinciple currentUser) throws IOException {

        ApiResponse apiResponse = calendarEventFacade.updateEvent(calendarEventRequest, currentUser.getUserID());
        return ResponseEntity.ok().body(apiResponse);

    }

    @DeleteMapping("deleteEvent")
    public ResponseEntity<?> updateEvent(@RequestParam(value = "eventID", required = true) String eventID,
                                         @CurrentUser CustomerPrinciple currentUser) {


        ApiResponse apiResponse = calendarEventFacade.deleteEvent(eventID, currentUser.getUserID());
        return ResponseEntity.ok().body(apiResponse);

    }
}
