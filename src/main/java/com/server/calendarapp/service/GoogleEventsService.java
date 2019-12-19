package com.server.calendarapp.service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.server.calendarapp.pojo.dbo.CalendarEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class GoogleEventsService {

    @Value("${application.name}")
    private String applicationName;

    @Qualifier("myFlow")
    private GoogleAuthorizationCodeFlow googleAuthorizationCodeFlow;
    private JsonFactory jsonFactory;
    private NetHttpTransport netHttpTransport;

    @Autowired
    public GoogleEventsService(GoogleAuthorizationCodeFlow googleAuthorizationCodeFlow
            , JsonFactory jsonFactory, NetHttpTransport netHttpTransport) {
        this.googleAuthorizationCodeFlow = googleAuthorizationCodeFlow;
        this.jsonFactory = jsonFactory;
        this.netHttpTransport = netHttpTransport;
    }


    public List<Event> getGoogleEvents(String userID) throws IOException {

        Calendar service = buildCalendar(userID);

        List<Event> eventList = service.events().list("primary").execute().getItems();

        return eventList;
    }

    public Event createEvent(CalendarEvent calendarEvent, String userID) throws IOException {

        Calendar service = buildCalendar(userID);

        System.out.println(calendarEvent.getUserID());
        Event event = new Event()
                .setId(calendarEvent.getEventID())
                .setSummary(calendarEvent.getEventName())
                .setDescription(calendarEvent.getDescription());

        Long startTime = Long.parseLong(calendarEvent.getStartTime());
        Long endTime = Long.parseLong(calendarEvent.getEndTime());

        DateTime startDateTime = new DateTime(startTime * 1000);
        DateTime endDateTime = new DateTime(endTime * 1000);

        EventDateTime start = new EventDateTime()
                .setDateTime(startDateTime);

        EventDateTime end = new EventDateTime()
                .setDateTime(endDateTime);

        event.setStart(start);
        event.setEnd(end);


        event = service.events().insert("primary", event).execute();

        return event;

    }


    public Event updateEvent(CalendarEvent calendarEvent, String userID) throws IOException {

        Calendar service = buildCalendar(userID);

        Event event = eventExist(service, calendarEvent.getEventID());

        event
                .setSummary(calendarEvent.getEventName())
                .setDescription(calendarEvent.getDescription());

        Long startTime = Long.parseLong(calendarEvent.getStartTime());
        Long endTime = Long.parseLong(calendarEvent.getEndTime());

        DateTime startDateTime = new DateTime(startTime * 1000);
        DateTime endDateTime = new DateTime(endTime * 1000);

        EventDateTime start = new EventDateTime()
                .setDateTime(startDateTime);

        EventDateTime end = new EventDateTime()
                .setDateTime(endDateTime);

        event.setStart(start);
        event.setEnd(end);


        Event updateEvent = service.events().update("primary", event.getId(), event).execute();

        return updateEvent;

    }


    public boolean deleteEvent(String eventID, String userID) throws IOException {

        Calendar service = buildCalendar(userID);
        Event event = eventExist(service, eventID);

        service.events().delete("primary", eventID).execute();


        return true;
    }

    private Calendar buildCalendar(String userID) throws IOException {
        Credential credential = googleAuthorizationCodeFlow.loadCredential(userID);
        Calendar clientCalendar = new Calendar.Builder(netHttpTransport, jsonFactory, credential)
                .setApplicationName(applicationName)
                .build();

        return clientCalendar;
    }

    private Event eventExist(Calendar service, String eventID) throws IOException {

        Event event = service.events().get("primary", eventID).execute();

        return event;
    }
}
