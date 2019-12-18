package com.server.calendarapp.service;

import com.server.calendarapp.exception.BadRequestException;
import com.server.calendarapp.payload.request.SignUpRequest;
import com.server.calendarapp.pojo.dbo.Customer;
import com.server.calendarapp.pojo.mapper.CustomerMapper;
import com.server.calendarapp.repository.CustomerRepository;
import com.server.calendarapp.security.CustomerPrinciple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private CustomerRepository customerRepository;

    @Autowired
    public CustomUserDetailService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {


        Customer customer = customerRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("user not found with email" + email));

        return CustomerPrinciple.build(customer);
    }


    @Transactional
    public UserDetails loadUserById(String id) throws UsernameNotFoundException {


        Customer customer = customerRepository.findByUserID(id)
                .orElseThrow(() -> new UsernameNotFoundException("user not found with id" + id));

        return CustomerPrinciple.build(customer);
    }

    @Transactional
    public Customer registerUser(SignUpRequest customer) {
        if (customerRepository.existsByEmail(customer.getEmail())) throw new BadRequestException("User already exists");

        Customer saveCustomer = CustomerMapper.mapSignUpRequestToCustomer(customer);

        return customerRepository.save(saveCustomer);

    }
}
