package com.mars.model;

import javax.validation.constraints.NotNull;

/**
 * Created by tachen on 5/12/2017.
 */
public class ActivityInfo {

    @NotNull
    private String name;

    @NotNull
    private String userId;

    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
