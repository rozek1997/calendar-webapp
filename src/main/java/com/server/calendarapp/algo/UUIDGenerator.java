package com.server.calendarapp.algo;

import java.util.UUID;

public class UUIDGenerator {

    public static String generateUnique() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
