package com.server.calendarapp.pojo.mapper;

import com.google.api.services.calendar.model.Event;
import com.server.calendarapp.pojo.dto.EventDTO;

import java.util.List;
import java.util.stream.Collectors;

public class GoogleEventMapper {

    private final static long MILISEC_IN_SEC = 1000;

    public static List<EventDTO> mapGoogleEventsToDTO(List<Event> googleEventList) {

        return googleEventList.stream().map(event -> {

            EventDTO newEvent = new EventDTO();

            event.getStart().getDateTime().getValue();
            newEvent
                    .setEventName(event.getSummary())
                    .setDescription(event.getDescription())
                    .setEventID(event.getId())
                    .setStartTime(Long.toString(event.getStart().getDateTime().getValue() / MILISEC_IN_SEC))
                    .setEndTime(Long.toString(event.getEnd().getDateTime().getValue() / MILISEC_IN_SEC));

            return newEvent;
        }).collect(Collectors.toList());
    }
}
