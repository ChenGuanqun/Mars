package com.mars.controller;

import com.mars.common.ParamConstants;
import com.mars.dao.entity.ActivityEntity;
import com.mars.exception.MarsException;
import com.mars.model.ActivityInfo;
import com.mars.model.ActivityUpdateInfo;
import com.mars.service.ActivityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.servlet.http.HttpServletRequest;

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
        @RequestHeader(value = ParamConstants.X_AUTHENTICATED_TOKEN) String token, HttpServletRequest request) throws MarsException
    {
        String currentUserId = (String) request.getSession().getAttribute(ParamConstants.CURRENT_USER_ID);
        if (!currentUserId.equals(userId)) {
            throw new MarsException(HttpStatus.UNAUTHORIZED, "The userId provided does not match your current logon userId!");
        }

        return activityService.getAllActivities(userId);
    }


    @RequestMapping(method = RequestMethod.GET, path = "/{activityId}")
    @ApiOperation(value = "Get activity for user", notes = "Get activity for user", response = ActivityEntity.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found"),
        @ApiResponse(code = 500, message = "Failure") })
    public ActivityEntity getActivity(@RequestHeader(value = ParamConstants.X_AUTHENTICATED_TOKEN) String token, @PathVariable String activityId,
        HttpServletRequest request) throws MarsException
    {
        String currentUserId = (String) request.getSession().getAttribute(ParamConstants.CURRENT_USER_ID);
        return activityService.getActivity(activityId, currentUserId);
    }


    @RequestMapping(method = RequestMethod.PATCH)
    @ApiOperation(value = "Partially update activity for user", notes = "Partially update activity for user", response = ActivityEntity.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found"),
        @ApiResponse(code = 500, message = "Failure") })
    public ActivityEntity updateActivity(@RequestHeader(value = ParamConstants.X_AUTHENTICATED_TOKEN) String token,
        @RequestBody ActivityUpdateInfo activityUpdateInfo) throws MarsException
    {

        return activityService.updateActivity(activityUpdateInfo);
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/{activityId}")
    @ApiOperation(value = "Partially update activity for user", notes = "Partially update activity for user", response = ActivityEntity.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found"),
        @ApiResponse(code = 500, message = "Failure") })
    public ActivityEntity deleteActivity(@RequestHeader(value = ParamConstants.X_AUTHENTICATED_TOKEN) String token, @PathVariable String activityId,
        HttpServletRequest request) throws MarsException
    {
        String currentUserId = (String) request.getSession().getAttribute(ParamConstants.CURRENT_USER_ID);
        return activityService.deleteActivity(activityId, currentUserId);
    }

}
