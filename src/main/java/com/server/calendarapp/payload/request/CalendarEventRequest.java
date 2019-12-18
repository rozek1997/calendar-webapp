package com.server.calendarapp.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CalendarEventRequest {

    private String eventID;
    @NonNull
    @NotBlank
    private String eventName;
    @NonNull
    @NotBlank
    private String startTime;
    @NonNull
    @NotBlank
    private String endTime;
    @NonNull
    private String description;
}
