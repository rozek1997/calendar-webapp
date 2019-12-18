package com.server.calendarapp.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class EventDTO {

    private String eventID;
    private String eventName;
    private String startTime;
    private String endTime;
    private String description;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventDTO eventDTO = (EventDTO) o;
        return eventID.equals(eventDTO.eventID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventID);
    }
}
