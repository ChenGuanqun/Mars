package com.mars.dao;

import com.mars.dao.entity.ActivityEntity;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by tachen on 5/9/2017.
 */
public interface ActivityRepository extends CrudRepository<ActivityEntity, Long> {
    @Query("select a from #{#entityName} a where a.userId=?1 and a.name = ?2")
    ActivityEntity findByUserIdAndName(String userId, String name);

    @Query("select a from #{#entityName} a where a.userId=?1")
    List<ActivityEntity> findByUserId(String userId);

    @Modifying
    @Query("update #{#entityName} a set name=?1 where a.id=?2")
    @Transactional
    void updateNameById(String name,String id);

    @Modifying
    @Query("update #{#entityName} a set description=?1 where a.id=?2")
    @Transactional
    void updateDescriptionById(String description,String id);

    @Modifying
    @Query("update #{#entityName} a set name=?1, description=?2 where a.id=?3")
    @Transactional
    void updateNameAndDescriptionById(String name, String description,String id);

    @Query("select a from #{#entityName} a where a.id=?1")
    @Transactional
    ActivityEntity findById(String id);

    @Modifying
    @Query("delete from #{#entityName} a where a.id=?1")
    @Transactional
    void deleteById(String id);

}
