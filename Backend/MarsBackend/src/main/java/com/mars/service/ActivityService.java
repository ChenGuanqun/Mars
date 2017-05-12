package com.mars.service;

import com.mars.dao.entity.ActivityEntity;
import com.mars.exception.MarsException;
import com.mars.model.ActivityInfo;

import java.util.List;

/**
 * Created by tachen on 5/12/2017.
 */
public interface ActivityService {

    ActivityEntity createActivity(ActivityInfo activityInfo) throws MarsException;

    List<ActivityEntity> getAllActivities(String userId) throws  MarsException;
}
