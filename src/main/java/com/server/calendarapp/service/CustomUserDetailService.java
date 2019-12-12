package com.server.calendarapp.service;

import com.server.calendarapp.pojo.Customer;
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
}
