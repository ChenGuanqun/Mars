package com.mars.service.impl;

import com.mars.dao.ActivityRepository;
import com.mars.dao.entity.ActivityEntity;
import com.mars.exception.MarsException;
import com.mars.model.ActivityInfo;
import com.mars.service.ActivityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.xml.ws.http.HTTPException;

/**
 * Created by tachen on 5/12/2017.
 */
@Service
public class ActivityServiceImpl implements ActivityService{

    @Autowired
    private ActivityRepository activityRepository;

    @Override
    public ActivityEntity createActivity(ActivityInfo activityInfo) throws MarsException {
        ActivityEntity activityEntityExists = activityRepository.findByUserIdAndName(activityInfo.getUserId(), activityInfo.getName());
        if(activityEntityExists != null){
            throw new MarsException(HttpStatus.BAD_REQUEST, "The activity exists, please try another name !");
        }
        ActivityEntity activityEntity = new ActivityEntity();
        activityEntity.setName(activityInfo.getName());
        activityEntity.setUserId(activityInfo.getUserId());
        activityEntity.setDescription(activityInfo.getDescription());
        return activityRepository.save(activityEntity);
    }

    @Override
    public List<ActivityEntity> getAllActivities(String userId) throws MarsException {
        return activityRepository.findByUserId(userId);
    }
}
