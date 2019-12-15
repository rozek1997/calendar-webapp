package com.server.calendarapp.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CalendarEventRequest {

    private String eventID;
    @NonNull
    private String eventName;
    @NonNull
    private String startTime;
    @NonNull
    private String endTime;
    @NonNull
    private String description;
}
