package com.mars.service;

import com.mars.dao.entity.User;
import com.mars.exception.MarsException;
import com.mars.model.UserInfo;


/**
 * Created by tachen on 5/10/2017.
 */
public interface UserService {
    User createUser(UserInfo userInfo) throws MarsException;

    boolean userLogon(String name, String password) throws MarsException;

}
