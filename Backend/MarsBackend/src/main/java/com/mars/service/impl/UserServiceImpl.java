package com.mars.service.impl;

import com.mars.dao.UserRepository;
import com.mars.dao.entity.UserEntity;
import com.mars.exception.MarsException;
import com.mars.model.UserInfo;
import com.mars.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Created by tachen on 5/10/2017.
 */

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger =
        LoggerFactory.getLogger(UserServiceImpl.class);

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserEntity createUser(UserInfo userInfo) throws MarsException {
        UserEntity user = new UserEntity();
        user.setName(userInfo.getName());
        user.setEmail(userInfo.getEmail());
        user.setPhone(userInfo.getPhone());
        user.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        try{
            userRepository.save(user);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            throw new MarsException(HttpStatus.INTERNAL_SERVER_ERROR,"Failed to create user");
        }
        return user;
    }

    @Override
    public UserEntity userLogon(String name, String password) throws MarsException {
        UserEntity user = userRepository.findByName(name);
        if(user == null){
            throw new MarsException(HttpStatus.NOT_FOUND, "User name not found!");
        }
        if(passwordEncoder.matches(password, user.getPassword())){
            return user;
        }else{
            throw new MarsException(HttpStatus.UNAUTHORIZED, "User name and password does not match!");
        }
    }
}
