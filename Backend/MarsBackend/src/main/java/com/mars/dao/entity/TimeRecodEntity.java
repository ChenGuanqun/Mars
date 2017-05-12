package com.mars.dao.entity;

import org.hibernate.annotations.GenericGenerator;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by tachen on 5/12/2017.
 */
@Entity
public class TimeRecodEntity {

    @Id
    @GeneratedValue(generator = "guid")
    @GenericGenerator(name = "guid", strategy = "guid")
    @Column(name = "id", unique = true)
    private String id;

    @Column(name = "userId")
    private String userId;

    @Column(name = "activityId")
    private String activityId;

    @Column(name = "startTime")
    private Date startTime;

    @Column(name = "endTime")
    private Date endTime;

    /**
     * Status of this activity for the user
     */
    @Column(name = "status")
    private int status;

}
