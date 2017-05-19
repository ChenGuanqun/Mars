package com.mars.service.impl;

import com.mars.dao.TimeRecordRepository;
import com.mars.dao.entity.TimeRecordEntity;
import com.mars.exception.MarsException;
import com.mars.model.TimeRecordInfo;
import com.mars.service.TimeRecordService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by tachen on 5/18/2017.
 */
@Service
public class TimeRecordServiceImpl implements TimeRecordService {

    @Autowired
    private TimeRecordRepository timeRecordRepository;

    @Override
    public TimeRecordEntity createTimeRecord(TimeRecordInfo timeRecordInfo) throws MarsException {
        TimeRecordEntity recordEntity = new TimeRecordEntity();
        recordEntity.setUserId(timeRecordInfo.getUserId());
        recordEntity.setActivityId(timeRecordInfo.getActivityId());
        recordEntity.setStartTime(timeRecordInfo.getStartTime());
        return timeRecordRepository.save(recordEntity);
    }

    @Override
    public TimeRecordEntity updateEndTimeRecord(String timeRecordId, Date endTime) throws MarsException {
        if (!timeRecordRepository.exists(timeRecordId)) {
            throw new MarsException(HttpStatus.NOT_FOUND, "Object not found for id " + timeRecordId);
        }
        timeRecordRepository.updateEndTimeRecord(timeRecordId, endTime);
        return timeRecordRepository.findOne(timeRecordId);
    }

    @Override
    public TimeRecordEntity updateStartTimeRecord(String timeRecordId, Date startTime) throws MarsException {
        if (!timeRecordRepository.exists(timeRecordId)) {
            throw new MarsException(HttpStatus.NOT_FOUND, "Object not found for id " + timeRecordId);
        }
        timeRecordRepository.updateStartTimeRecord(timeRecordId, startTime);
        return timeRecordRepository.findOne(timeRecordId);
    }

    @Override
    public TimeRecordEntity updateActivityRecord(String timeRecordId, String activityId) throws MarsException {
        if (!timeRecordRepository.exists(timeRecordId)) {
            throw new MarsException(HttpStatus.NOT_FOUND, "Object not found for id " + timeRecordId);
        }
        timeRecordRepository.updateActivityRecord(timeRecordId, activityId);
        return timeRecordRepository.findOne(timeRecordId);
    }
}
