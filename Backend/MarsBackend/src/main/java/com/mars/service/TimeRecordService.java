package com.mars.service;

import com.mars.dao.entity.TimeRecordEntity;
import com.mars.exception.MarsException;
import com.mars.model.TimeRecordInfo;

import java.util.Date;

/**
 * Created by tachen on 5/18/2017.
 */
public interface TimeRecordService {

    TimeRecordEntity createTimeRecord(TimeRecordInfo timeRecordInfo) throws MarsException;

    TimeRecordEntity updateEndTimeRecord(String timeRecordId, Date endTime) throws MarsException;

    TimeRecordEntity updateStartTimeRecord(String timeRecordId, Date startTime) throws MarsException;

    TimeRecordEntity updateActivityRecord(String timeRecordId, String activityId) throws MarsException;
}
