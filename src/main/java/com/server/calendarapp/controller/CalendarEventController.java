package com.server.calendarapp.controller;

import com.server.calendarapp.payload.request.CalendarEventRequest;
import com.server.calendarapp.payload.response.ApiResponse;
import com.server.calendarapp.payload.response.CalendarEventsResponse;
import com.server.calendarapp.security.CurrentUser;
import com.server.calendarapp.security.CustomerPrinciple;
import com.server.calendarapp.service.CalendarEventFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/api/calendarevents")
public class CalendarEventController {

    private CalendarEventFacade calendarEventFacade;

    @Autowired
    public CalendarEventController(CalendarEventFacade calendarEventFacade) {
        this.calendarEventFacade = calendarEventFacade;
    }

    /**
     * @param currentUser autheticated via spring security
     * @return ResponseEntity with http status
     */
    @GetMapping
    public ResponseEntity<?> getAllUsersEvent(@CurrentUser CustomerPrinciple currentUser) {
        CalendarEventsResponse response = calendarEventFacade.getAllEvents(currentUser.getUserID());
        return new ResponseEntity(response, response.getApiResponse().getHttpStatus());
    }

    /**
     * @param calendarEventRequest
     * @param currentUser          authenticated via spring security
     * @return
     */
    @PostMapping("createEvent")
    public ResponseEntity<?> createEvent(@Valid @RequestBody CalendarEventRequest calendarEventRequest
            , @CurrentUser CustomerPrinciple currentUser) {

        ApiResponse apiResponse = calendarEventFacade.createEvent(calendarEventRequest, currentUser.getUserID());
        return mapStatus(apiResponse);
    }

    /**
     * @param calendarEventRequest
     * @param currentUser
     * @return
     * @throws IOException
     */
    @PutMapping("updateEvent")
    public ResponseEntity<?> updateEvent(@Valid @RequestBody CalendarEventRequest calendarEventRequest
            , @CurrentUser CustomerPrinciple currentUser) throws IOException {

        ApiResponse apiResponse = calendarEventFacade.updateEvent(calendarEventRequest, currentUser.getUserID());
        return mapStatus(apiResponse);

    }

    /**
     * @param eventID
     * @param currentUser
     * @return
     */
    @DeleteMapping("deleteEvent")
    public ResponseEntity<?> deleteEvent(@RequestParam(value = "eventID", required = true) String eventID,
                                         @CurrentUser CustomerPrinciple currentUser) {

        ApiResponse apiResponse = calendarEventFacade.deleteEvent(eventID, currentUser.getUserID());
        return mapStatus(apiResponse);

    }


    private ResponseEntity<?> mapStatus(ApiResponse apiResponse) {


        if (apiResponse.getHttpStatus() == HttpStatus.PARTIAL_CONTENT)
            return new ResponseEntity(apiResponse, HttpStatus.PARTIAL_CONTENT);

        if (apiResponse.getHttpStatus() == HttpStatus.NOT_FOUND)
            return new ResponseEntity(apiResponse, HttpStatus.MULTI_STATUS);

        if (apiResponse.getHttpStatus() == HttpStatus.CREATED)
            return new ResponseEntity(apiResponse, HttpStatus.CREATED);

        return ResponseEntity.ok().body(apiResponse);

    }
}
