package com.mars.service.impl;

import com.mars.dao.ActivityRepository;
import com.mars.dao.UserRepository;
import com.mars.dao.entity.ActivityEntity;
import com.mars.exception.MarsException;
import com.mars.model.ActivityInfo;
import com.mars.model.ActivityUpdateInfo;
import com.mars.service.ActivityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;


/**
 * Created by tachen on 5/12/2017.
 */
@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public ActivityEntity createActivity(ActivityInfo activityInfo) throws MarsException {

        ActivityEntity activityEntityExists = activityRepository.findByUserIdAndName(activityInfo.getUserId(), activityInfo.getName());
        if (activityEntityExists != null) {
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

    @Override
    public ActivityEntity updateActivity(ActivityUpdateInfo activityUpdateInfo) throws MarsException {

        String name = activityUpdateInfo.getName();
        String des = activityUpdateInfo.getDescription();
        String id = activityUpdateInfo.getId();

        if (!StringUtils.isEmpty(name) && (!StringUtils.isEmpty(des))) {
            activityRepository.updateNameAndDescriptionById(name, des, id);
        } else if (!StringUtils.isEmpty(name)) {
            activityRepository.updateNameById(name, id);
        } else if (!StringUtils.isEmpty(des)) {
            activityRepository.updateDescriptionById(des, id);
        } else {
            throw new MarsException(HttpStatus.BAD_REQUEST, "Please pass correct name or description to modify!");
        }
        return activityRepository.findById(activityUpdateInfo.getId());
    }

    @Override
    public ActivityEntity deleteActivity(String activityId, String currentUserId) throws MarsException {
        ActivityEntity activityEntity = activityRepository.findById(activityId);
        if (activityEntity == null) {
            throw new MarsException(HttpStatus.NOT_FOUND, "Activity not found Id " + activityId + " ,please pass valid activity id!");
        }
        if (!activityEntity.getUserId().equals(currentUserId)) {
            throw new MarsException(HttpStatus.UNAUTHORIZED, "You don not have access to delete this due to userId passed does not match current user!");
        } else {
            activityRepository.deleteById(activityId);
        }

        return activityEntity;
    }

    @Override
    public ActivityEntity getActivity(String activityId, String currentUserId) throws MarsException {

        ActivityEntity activityEntity = activityRepository.findById(activityId);
        if (activityEntity == null) {
            throw new MarsException(HttpStatus.NOT_FOUND, "Activity not found Id " + activityId + " ,please pass valid activity id!");
        }
        if (!activityEntity.getUserId().equals(currentUserId)) {
            throw new MarsException(HttpStatus.UNAUTHORIZED, "You don not have access to delete this due to userId passed does not match current user!");
        }
        return activityEntity;
    }
}
