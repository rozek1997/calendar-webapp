package com.server.calendarapp.model;

import com.server.calendarapp.pojo.User;
import com.server.calendarapp.pojo.UserPrinciple;
import com.server.calendarapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public CustomUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {


        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("user not found with email" + email));

        return UserPrinciple.build(user);
    }
}
