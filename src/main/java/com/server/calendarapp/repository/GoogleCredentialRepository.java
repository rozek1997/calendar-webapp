package com.server.calendarapp.repository;

import com.server.calendarapp.pojo.dbo.GoogleCredential;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@EnableScan
@Repository
public interface GoogleCredentialRepository extends CrudRepository<GoogleCredential, String> {

    Optional<GoogleCredential> findByUserID(String userID);

    Optional<GoogleCredential> findByAccessToken(String token);

    void deleteByUserID(String userID);

    Set<String> findAllByUserID();
}
