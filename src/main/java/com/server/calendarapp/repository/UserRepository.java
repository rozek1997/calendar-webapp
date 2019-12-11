package com.server.calendarapp.repository;


import com.server.calendarapp.pojo.User;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

@EnableScan
public interface UserRepository extends CrudRepository<User, String> {

    Optional<User> findUserByEmail(String email);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
