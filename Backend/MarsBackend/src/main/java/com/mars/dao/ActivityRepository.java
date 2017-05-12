package com.mars.dao;

import com.mars.dao.entity.ActivityEntity;
import com.mars.dao.entity.UserEntity;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by tachen on 5/9/2017.
 */
public interface ActivityRepository extends CrudRepository<ActivityEntity, Long> {
    @Query("select a from #{#entityName} a where a.userId=?1 and a.name = ?2")
    ActivityEntity findByUserIdAndName(String userId, String name);

    @Query("select a from #{#entityName} a where a.userId=?1")
    List<ActivityEntity> findByUserId(String userId);
}
