package com.server.calendarapp.payload;

import com.server.calendarapp.pojo.CalendarEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CalendarEventsResponse {

    private Timestamp createdAt;
    private List<CalendarEvent> calendarEventList;


}
