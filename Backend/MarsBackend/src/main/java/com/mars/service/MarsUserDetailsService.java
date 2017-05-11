package com.mars.service;

import com.mars.dao.UserRepository;
import com.mars.dao.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by tachen on 2017/5/11.
 */
@Service
public class MarsUserDetailsService implements UserDetailsService {


    @Autowired
    private UserRepository userRepository;


    @Override
    public MarsUser loadUserByUsername(String name) throws UsernameNotFoundException {
        //User user = userService.getUserByEmail(email)
        //       .orElseThrow(() -> new UsernameNotFoundException(String.format("User with email=%s was not found", email)));
        //return new MarsUser(user);
        //userRepository.
        User user = new User();
        user.setName("abc");
        user.setPassword("qwe");
        MarsUser marsUser = new MarsUser(user);
        return marsUser;

    }
}
