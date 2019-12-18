package com.server.calendarapp.pojo.mapper;

import com.server.calendarapp.payload.request.SignUpRequest;
import com.server.calendarapp.pojo.dbo.Customer;

public class CustomerMapper {


    public static Customer mapSignUpRequestToCustomer(SignUpRequest signUpRequest) {
        Customer customer = new Customer();
        customer
                .setEmail(signUpRequest.getEmail())
                .setPassword(signUpRequest.getPassword())
                .setUsername(signUpRequest.getUsername());

        return customer;
    }
}
