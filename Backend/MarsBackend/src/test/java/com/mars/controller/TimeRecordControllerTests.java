package com.mars.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mars.common.ParamConstants;
import com.mars.dao.entity.ActivityEntity;
import com.mars.dao.entity.TimeRecordEntity;
import com.mars.dao.entity.UserEntity;
import com.mars.filter.AuthenticatedFilter;
import com.mars.model.ActivityInfo;
import com.mars.model.TimeRecordInfo;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by tachen on 5/18/2017.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TimeRecordControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    static String activityName = "activity-" + (new BigInteger(130, new SecureRandom())).toString(32);

    static String activityDes = "des-" + (new BigInteger(130, new SecureRandom())).toString(32);


    static String token;

    static String userId;

    static MockHttpSession session;

    static String activityId;

    static String timeRecordId;

    @Before
    public void init() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
            .addFilter(new AuthenticatedFilter(), "/*")
            .build();
    }

    @Test
    public void t1_createSession() throws Exception {

        MvcResult mvcResult = this.mockMvc.perform(post("/api/users/session").header("USER-NAME", "admin").header("PASSWORD", "admin"))
            .andDo(print()).andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("admin")).andReturn();

        token = mvcResult.getResponse().getHeader(ParamConstants.X_AUTHENTICATED_TOKEN);
        String body = mvcResult.getResponse().getContentAsString();
        UserEntity user = new ObjectMapper().readValue(body, UserEntity.class);
        userId = user.getId();
        session = (MockHttpSession) mvcResult.getRequest().getSession();
    }

    @Test
    public void t2_createActivity() throws Exception {
        ActivityInfo activityInfo = new ActivityInfo();
        activityInfo.setName(activityName);
        activityInfo.setDescription(activityDes);
        activityInfo.setUserId(userId);

        MvcResult mvcResult = this.mockMvc
            .perform(post("/api/activities").session(session).contentType(
                MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(activityInfo))
                .header(ParamConstants.X_AUTHENTICATED_TOKEN, token))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value(activityName))
            .andReturn();
        String body = mvcResult.getResponse().getContentAsString();
        ActivityEntity activityEntity = new ObjectMapper().readValue(body, ActivityEntity.class);
        activityId = activityEntity.getId();
    }

    //    @Test
    //    public void t2_getActivities() throws Exception {
    //        MvcResult mvcResult = this.mockMvc.perform(get("/api/activities").session(session).param("userId", userId)
    //            .header(ParamConstants.X_AUTHENTICATED_TOKEN, token))
    //            .andDo(print()).andExpect(status().isOk()).andReturn();
    //        String body = mvcResult.getResponse().getContentAsString();
    //        ActivityEntity[] activityEntities = new ObjectMapper().readValue(body, ActivityEntity[].class);
    //        if (activityEntities == null || activityEntities.length <= 0) {
    //            throw new Exception("No activity found for this user, please add it before run this test!");
    //        }
    //        activityId = activityEntities[0].getId();
    //        System.out.println("The number of activities : " + activityEntities.length);
    //    }

    @Test
    public void t3_createTimeRecord() throws Exception {
        Date startTime = new Date();
        TimeRecordInfo timeRecordInfo = new TimeRecordInfo();
        timeRecordInfo.setUserId(userId);
        timeRecordInfo.setActivityId(activityId);
        timeRecordInfo.setStartTime(startTime);
        MvcResult mvcResult = this.mockMvc.perform(post("/api/timeRecords").session(session).contentType(
            MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(timeRecordInfo))
            .header(ParamConstants.X_AUTHENTICATED_TOKEN, token))
            .andDo(print()).andExpect(status().isOk())
            .andExpect(jsonPath("$.startTime").value(new ObjectMapper().writeValueAsString(startTime))).andReturn();

        String body = mvcResult.getResponse().getContentAsString();
        TimeRecordEntity timeRecordEntity = new ObjectMapper().readValue(body, TimeRecordEntity.class);
        timeRecordId = timeRecordEntity.getId();
    }

    @Test
    public void t4_updateEndTimeRecord() throws Exception {
        Date endTime = new Date();
        String expectedTime = new ObjectMapper().writeValueAsString(endTime);
        expectedTime = expectedTime.substring(0,expectedTime.length()-3) + "000";
        MvcResult mvcResult = this.mockMvc.perform(patch("/api/timeRecords/" + timeRecordId + "/endTime").session(session).contentType(
            MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(endTime))
            .header(ParamConstants.X_AUTHENTICATED_TOKEN, token))
            .andDo(print()).andExpect(status().isOk())
            .andExpect(jsonPath("$.endTime").value(expectedTime)).andReturn();

        Thread.sleep(2000);
    }

    @Test
    public void t5_updateEndTimeRecord() throws Exception {
        Date endTime = new Date();
        String expectedTime = new ObjectMapper().writeValueAsString(endTime);
        expectedTime = expectedTime.substring(0,expectedTime.length()-3) + "000";
        MvcResult mvcResult = this.mockMvc.perform(patch("/api/timeRecords/" + timeRecordId + "/endTime").session(session).contentType(
            MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(endTime))
            .header(ParamConstants.X_AUTHENTICATED_TOKEN, token))
            .andDo(print()).andExpect(status().isOk())
            .andExpect(jsonPath("$.endTime").value(expectedTime)).andReturn();
    }



}
