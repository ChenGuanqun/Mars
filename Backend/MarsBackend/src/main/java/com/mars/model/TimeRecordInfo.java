package com.mars.model;

import java.util.Date;

import javax.validation.constraints.NotNull;

/**
 * Created by tachen on 5/18/2017.
 */
public class TimeRecordInfo {

    @NotNull
    private String userId;

    @NotNull
    private String activityId;

    @NotNull
    private Date startTime;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
}
