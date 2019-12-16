package com.server.calendarapp.service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.services.calendar.Calendar;
import com.server.calendarapp.security.CustomerPrinciple;
import jdk.jfr.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

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

    @Async
    public CompletableFuture<List<Event>> getGoogleEvents() {


        googleAuthorizationCodeFlow.n
    }

    private Calendar buildCalendar(CustomerPrinciple currentUser) throws IOException {
        Credential credential = googleAuthorizationCodeFlow.loadCredential(currentUser.getUserID());
        Calendar clientCalendar = new Calendar.Builder(netHttpTransport, jsonFactory, credential)
                .setApplicationName(applicationName)
                .build();
    }
}
