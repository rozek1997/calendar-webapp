package com.server.calendarapp.payload.response;

import com.server.calendarapp.pojo.dto.EventDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CalendarEventsResponse {

    private Timestamp createdAt;
    private Set<EventDTO> calendarEventList;


}
