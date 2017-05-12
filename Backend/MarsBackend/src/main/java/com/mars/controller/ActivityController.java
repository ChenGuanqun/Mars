package com.mars.controller;

import com.mars.common.ParamConstants;
import com.mars.dao.entity.ActivityEntity;
import com.mars.exception.MarsException;
import com.mars.model.ActivityInfo;
import com.mars.service.ActivityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.List;

/**
 * Created by tachen on 5/12/2017.
 */
@RestController
@RequestMapping(path = "/api/activities")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value = "Create a new activity for user", notes = "Create a new activity for user")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success", response = ActivityEntity.class),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found"),
        @ApiResponse(code = 500, message = "Failure") })
    public ActivityEntity addNewActivity(@RequestBody ActivityInfo activityInfo,
        @RequestHeader(value = ParamConstants.X_AUTHENTICATED_TOKEN) String token) throws MarsException
    {
        return activityService.createActivity(activityInfo);
    }


    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation(value = "Get all activities for user", notes = "Get all activities for user", response = ActivityEntity.class, responseContainer = "List")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found"),
        @ApiResponse(code = 500, message = "Failure") })
    public List<ActivityEntity> getActivities(@RequestParam("userId") String userId,
        @RequestHeader(value = ParamConstants.X_AUTHENTICATED_TOKEN) String token) throws MarsException
    {
        return activityService.getAllActivities(userId);
    }

}
