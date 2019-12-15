package com.server.calendarapp.repository.google;

import com.google.api.client.util.store.AbstractDataStoreFactory;
import com.server.calendarapp.repository.GoogleCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GoogleCalendarDataStoreFactory extends AbstractDataStoreFactory {


    private GoogleCredentialRepository googleCredentialRepository;

    @Autowired
    public GoogleCalendarDataStoreFactory(GoogleCredentialRepository googleCredentialRepository) {
        this.googleCredentialRepository = googleCredentialRepository;
    }

    @Override
    protected GoogleCalendarDataStore createDataStore(String s) {
        return new GoogleCalendarDataStore(this, s, googleCredentialRepository);
    }
}
