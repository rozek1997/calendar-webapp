package com.server.calendarapp.service;

import com.server.calendarapp.exception.EventsNotFoundException;
import com.server.calendarapp.payload.request.CalendarEventRequest;
import com.server.calendarapp.pojo.dbo.CalendarEvent;
import com.server.calendarapp.pojo.mapper.CalendarEventMapper;
import com.server.calendarapp.repository.CalendarEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CalendarEventsService {


    private CalendarEventRepository calendarEventRepository;

    @Autowired
    public CalendarEventsService(CalendarEventRepository calendarEventRepository) {
        this.calendarEventRepository = calendarEventRepository;
    }


    public List<CalendarEvent> getAllEvents(String userID) throws EventsNotFoundException {

        List<CalendarEvent> calendarEventList = calendarEventRepository
                .findByUserID(userID)
                .orElseThrow(() -> new EventsNotFoundException("Cant find events for user" + userID));

        return calendarEventList;
    }


    public CalendarEvent createEvent(CalendarEventRequest event, String userID) {

        CalendarEvent calendarEvent = CalendarEventMapper.mapRequestToCalendarEvent(event, userID);
        return calendarEventRepository.save(calendarEvent);
    }

    public CalendarEvent updateEvent(CalendarEventRequest event, String userID) throws EventsNotFoundException {
        CalendarEvent calendarEvent = CalendarEventMapper.mapRequestToCalendarEvent(event, userID);
        CalendarEvent returnEvent = calendarEventRepository.findByEventID(calendarEvent.getEventID());
        if (returnEvent == null)
            throw new EventsNotFoundException("Event not found " + calendarEvent.getEventID());
        return calendarEventRepository.save(calendarEvent);
    }


    public void deleteEvent(String eventID) throws EventsNotFoundException {

        CalendarEvent event = calendarEventRepository.findByEventID(eventID);
        if (event == null) {
            throw new EventsNotFoundException("Event not found " + eventID);
        }
        calendarEventRepository.deleteById(eventID);

    }

}
