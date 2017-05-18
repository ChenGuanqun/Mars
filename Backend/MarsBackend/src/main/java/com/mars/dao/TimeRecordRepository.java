package com.mars.dao;

import com.mars.dao.entity.ActivityEntity;
import com.mars.dao.entity.TimeRecordEntity;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


/**
 * Created by tachen on 5/18/2017.
 */
public interface TimeRecordRepository extends CrudRepository<TimeRecordEntity, Long> {

    @Query("select a from #{#entityName} a where a.id=?1")
    @Transactional
    TimeRecordEntity findById(String id);

    @Modifying
    @Query("update #{#entityName} a set endTime=?2 where a.id=?1")
    @Transactional
    void updateEndTimeRecord(String timeRecordId, Date endTime);

    @Modifying
    @Query("update #{#entityName} a set startTime=?2 where a.id=?1")
    @Transactional
    void updateStartTimeRecord(String timeRecordId, Date startTime);

    @Modifying
    @Query("update #{#entityName} a set activityId=?2 where a.id=?1")
    @Transactional
    void updateActivityRecord(String timeRecordId, String activityId);

}
