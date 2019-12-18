package com.server.calendarapp.exception;

import java.io.IOException;

public class EventsNotFoundException extends IOException {

    public EventsNotFoundException(String message) {
        super(message);
    }
}
