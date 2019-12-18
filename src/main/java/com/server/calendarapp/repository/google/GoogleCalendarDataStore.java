package com.server.calendarapp.repository.google;

import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.util.store.AbstractDataStore;
import com.google.api.client.util.store.DataStore;
import com.google.api.client.util.store.DataStoreFactory;
import com.server.calendarapp.pojo.dbo.GoogleCredential;
import com.server.calendarapp.repository.GoogleCredentialRepository;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class GoogleCalendarDataStore extends AbstractDataStore<StoredCredential> {


    private GoogleCredentialRepository repository;

    protected GoogleCalendarDataStore(DataStoreFactory dataStoreFactory, String id,
                                      GoogleCredentialRepository repository) {
        super(dataStoreFactory, id);
        this.repository = repository;
    }

    @Override
    public DataStoreFactory getDataStoreFactory() {
        return super.getDataStoreFactory();
    }

    @Override
    public boolean containsKey(String key) throws IOException {
        return repository.findByUserID(key).isPresent();
    }

    @Override
    public boolean containsValue(StoredCredential value) throws IOException {
        return repository.findByAccessToken(value.getAccessToken()).isPresent();
    }

    @Override
    public boolean isEmpty() throws IOException {
        return size() == 0;
    }

    @Override
    public int size() throws IOException {
        return (int) repository.count();
    }

    @Override
    public Set<String> keySet() throws IOException {
        return repository.findAllByUserID();
    }

    @Override
    public Collection<StoredCredential> values() throws IOException {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .map(c -> {
                    StoredCredential storedCredential = new StoredCredential();
                    storedCredential.setAccessToken(c.getAccessToken());
                    storedCredential.setRefreshToken(c.getRefreshToken());
                    storedCredential.setExpirationTimeMilliseconds(Long.parseLong(c.getExpirationTime()));

                    return storedCredential;
                }).collect(Collectors.toList());
    }

    @Override
    public StoredCredential get(String s) throws IOException {
        Optional<GoogleCredential> storedCredentialOptional = repository.findByUserID(s);
        if (!storedCredentialOptional.isPresent()) {
            return null;
        }
        GoogleCredential googleCredential = storedCredentialOptional.get();
        StoredCredential credential = new StoredCredential();
        credential.setAccessToken(googleCredential.getAccessToken());
        credential.setRefreshToken(googleCredential.getRefreshToken());
        credential.setExpirationTimeMilliseconds(Long.parseLong(googleCredential.getExpirationTime()));
        return credential;
    }

    @Override
    public DataStore<StoredCredential> set(String s, StoredCredential credential) throws IOException {
        GoogleCredential googleCredential = repository.findByUserID(s).orElse(new GoogleCredential(s, credential));
        googleCredential.apply(credential);
        repository.save(googleCredential);
        return this;
    }

    @Override
    public DataStore<StoredCredential> clear() throws IOException {
        repository.deleteAll();
        return this;
    }

    @Override
    public DataStore<StoredCredential> delete(String s) throws IOException {
        repository.deleteByUserID(s);
        return this;
    }
}
