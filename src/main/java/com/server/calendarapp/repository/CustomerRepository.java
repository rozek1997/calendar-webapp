package com.server.calendarapp.repository;


import com.server.calendarapp.pojo.dbo.Customer;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@EnableScan
@Repository
public interface CustomerRepository extends CrudRepository<Customer, String> {

    Optional<Customer> findUserByEmail(String email);

    Optional<Customer> findByUserID(String id);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
