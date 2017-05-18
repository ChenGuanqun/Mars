package com.mars.controller;

import com.mars.common.ParamConstants;
import com.mars.dao.entity.TimeRecordEntity;
import com.mars.exception.MarsException;
import com.mars.model.TimeRecordInfo;
import com.mars.service.TimeRecordService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.Date;

/**
 * Created by tachen on 5/12/2017.
 */
@RestController
@RequestMapping(path = "/api/timeRecords")
public class TimeRecordController {

    @Autowired
    private TimeRecordService timeRecordService;

    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value = "Start a new time record for user and activity", notes = "Start a new time record for user and activity")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success", response = TimeRecordEntity.class),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found"),
        @ApiResponse(code = 500, message = "Failure") })
    public TimeRecordEntity addNewTimeRecord(@RequestBody TimeRecordInfo timeRecordInfo,
        @RequestHeader(value = ParamConstants.X_AUTHENTICATED_TOKEN) String token) throws MarsException
    {
        return timeRecordService.createTimeRecord(timeRecordInfo);
    }

    @RequestMapping(method = RequestMethod.PATCH, path = "/{timeRecordId}/endTime")
    @ApiOperation(value = "Update the end time for time record for user and activity", notes = "Update the end time for time record for user and activity")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success", response = TimeRecordEntity.class),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found"),
        @ApiResponse(code = 500, message = "Failure") })
    public TimeRecordEntity updateEndTimeRecord(@RequestBody Date endTime, @PathVariable String timeRecordId,
        @RequestHeader(value = ParamConstants.X_AUTHENTICATED_TOKEN) String token) throws MarsException
    {
        return timeRecordService.updateEndTimeRecord(timeRecordId, endTime);
    }

    @RequestMapping(method = RequestMethod.PATCH, path = "/{timeRecordId}/startTime")
    @ApiOperation(value = "Update the start time for time record for user and activity", notes = "Update the start time for time record for user and activity")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success", response = TimeRecordEntity.class),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found"),
        @ApiResponse(code = 500, message = "Failure") })
    public TimeRecordEntity updateStartTimeRecord(@RequestBody Date startTime, @PathVariable String timeRecordId,
        @RequestHeader(value = ParamConstants.X_AUTHENTICATED_TOKEN) String token) throws MarsException
    {
        return timeRecordService.updateStartTimeRecord(timeRecordId, startTime);
    }

    @RequestMapping(method = RequestMethod.PATCH, path = "/{timeRecordId}/activity")
    @ApiOperation(value = "Update the activityId for time record for user and activity", notes = "Update the activityId for time record for user and activity")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success", response = TimeRecordEntity.class),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found"),
        @ApiResponse(code = 500, message = "Failure") })
    public TimeRecordEntity updateActivityTimeRecord(@RequestBody String activityId, @PathVariable String timeRecordId,
        @RequestHeader(value = ParamConstants.X_AUTHENTICATED_TOKEN) String token) throws MarsException
    {
        return timeRecordService.updateActivityRecord(timeRecordId, activityId);
    }
}
