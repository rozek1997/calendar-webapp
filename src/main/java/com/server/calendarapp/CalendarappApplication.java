package com.server.calendarapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.server.calendarapp", "com.server.calendarapp.repository"})
public class CalendarappApplication {

    public static void main(String[] args) {
        SpringApplication.run(CalendarappApplication.class, args);
    }

}
