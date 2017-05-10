package com.mars.dao;

import com.mars.dao.entity.User;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by tachen on 5/9/2017.
 */
public interface UserRepository extends CrudRepository<User, Long> {

}
