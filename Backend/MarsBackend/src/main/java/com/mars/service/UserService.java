package com.mars.service;

import com.mars.dao.entity.UserEntity;
import com.mars.exception.MarsException;
import com.mars.model.UserInfo;


/**
 * Created by tachen on 5/10/2017.
 */
public interface UserService {
    UserEntity createUser(UserInfo userInfo) throws MarsException;

    UserEntity userLogon(String name, String password) throws MarsException;

}
