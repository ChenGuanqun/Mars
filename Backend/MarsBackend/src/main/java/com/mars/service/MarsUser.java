package com.mars.service;

import com.mars.dao.entity.User;
import org.springframework.security.core.authority.AuthorityUtils;

/**
 * Created by tachen on 2017/5/11.
 */
public class MarsUser extends  org.springframework.security.core.userdetails.User{
    private User user;

    public MarsUser(User user) {
        super(user.getName(), user.getPassword(), AuthorityUtils.createAuthorityList(user.getName()));
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public String getId() {
        return user.getId();
    }


}
