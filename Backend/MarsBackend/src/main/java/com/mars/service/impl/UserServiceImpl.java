package com.mars.service.impl;

import com.mars.dao.UserRepository;
import com.mars.dao.entity.User;
import com.mars.exception.MarsException;
import com.mars.model.UserInfo;
import com.mars.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 * Created by tachen on 5/10/2017.
 */

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger =
        LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(UserInfo userInfo) throws MarsException {
        User user = new User();
        user.setName(userInfo.getName());
        user.setEmail(userInfo.getEmail());
        user.setPhone(userInfo.getPhone());
        user.setPassword(userInfo.getPassword());
        try{
            userRepository.save(user);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            throw new MarsException(HttpStatus.INTERNAL_SERVER_ERROR,"Failed to create user");
        }
        return user;
    }
}
