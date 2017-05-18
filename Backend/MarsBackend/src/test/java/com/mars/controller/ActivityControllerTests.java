package com.mars.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mars.common.ParamConstants;
import com.mars.dao.entity.ActivityEntity;
import com.mars.dao.entity.UserEntity;
import com.mars.exception.MarsException;
import com.mars.filter.AuthenticatedFilter;
import com.mars.model.ActivityInfo;
import com.mars.model.ActivityUpdateInfo;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigInteger;
import java.security.SecureRandom;


/**
 * @author tachen
 *         This class is used to perform the integrated test, environment required:
 *         1. User registed with name 'admin' and passwd 'admin'
 *         2. Mysql configured
 *         3. All the test should be performed with ascending sequence by function name
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ActivityControllerTests {

    @Autowired
    private MockMvc mockMvc;

    static String token;

    static String userId;

    static MockHttpSession session;

    static ActivityEntity[] activityEntities;

    static String activityName = "activity-" + (new BigInteger(130, new SecureRandom())).toString(32);

    static String activityDes = "des-" + (new BigInteger(130, new SecureRandom())).toString(32);

    String activityNameModify = activityName + "-Modify";

    String activityDesModify = activityDes + "-Modify";

    static String activityId;

    @Autowired
    private WebApplicationContext context;

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

    @Test
    public void t3_modifyActivity() throws Exception {
        ActivityUpdateInfo activityUpdateInfo = new ActivityUpdateInfo();
        activityUpdateInfo.setId(activityId);
        activityUpdateInfo.setName(activityNameModify);
        activityUpdateInfo.setDescription(activityDesModify);

        MvcResult mvcResult = this.mockMvc
            .perform(patch("/api/activities").session(session).contentType(
                MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(activityUpdateInfo))
                .header(ParamConstants.X_AUTHENTICATED_TOKEN, token))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value(activityNameModify))
            .andReturn();
    }

    @Test
    public void t4_getActivity() throws Exception {
        MvcResult mvcResult = this.mockMvc
            .perform(get("/api/activities/" + activityId).session(session)
                .header(ParamConstants.X_AUTHENTICATED_TOKEN, token))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value(activityNameModify))
            .andExpect(jsonPath("$.description").value(activityDesModify))
            .andExpect(jsonPath("$.id").value(activityId))
            .andExpect(jsonPath("$.userId").value(userId))
            .andReturn();

    }

    @Test
    public void t5_deleteActivity() throws Exception {
        MvcResult mvcResult = this.mockMvc
            .perform(delete("/api/activities/" + activityId).session(session)
                .header(ParamConstants.X_AUTHENTICATED_TOKEN, token))
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn();

    }

    @Test
    public void t6_getActivitiesWithoutJSESSIONID() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/api/activities").param("userId", userId)
            .header(ParamConstants.X_AUTHENTICATED_TOKEN, token))
            .andDo(print()).andExpect(status().isUnauthorized()).andReturn();
    }

    @Test
    public void t7_getActivitiesWithoutToken() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/api/activities").session(session).param("userId", userId))
            //            .header(ParamConstants.X_AUTHENTICATED_TOKEN, token))
            .andDo(print()).andExpect(status().isUnauthorized()).andReturn();
    }

    @Test
    public void t9_getActivities() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/api/activities").session(session).param("userId", userId)
            .header(ParamConstants.X_AUTHENTICATED_TOKEN, token))
            .andDo(print()).andExpect(status().isOk()).andReturn();
        String body = mvcResult.getResponse().getContentAsString();
        activityEntities = new ObjectMapper().readValue(body, ActivityEntity[].class);
        System.out.println("The number of activities : " + activityEntities.length);
    }
}
