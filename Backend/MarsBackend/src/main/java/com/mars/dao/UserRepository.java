package com.mars.dao;

import com.mars.dao.entity.User;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by tachen on 5/9/2017.
 */
public interface UserRepository extends CrudRepository<User, Long> {
    @Query("select u from #{#entityName} u where u.name = ?1")
    User findByName(String name);
}
