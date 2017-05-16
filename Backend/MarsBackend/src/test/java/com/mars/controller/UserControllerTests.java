/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mars.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mars.common.ParamConstants;
import com.mars.dao.entity.UserEntity;
import com.mars.filter.AuthenticatedFilter;
import com.mars.filter.WebComponentConfig;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@RunWith( SpringJUnit4ClassRunner.class )
@SpringBootTest
@ComponentScan("com.mars.*")
@AutoConfigureMockMvc
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    static String token;

    static String userId;

    static MockHttpSession session;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void init() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
            .addFilter(new AuthenticatedFilter(), "/*")
            .build();
    }

    @Test
    public void createSession() throws Exception {

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
    public void getActivities() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/api/activities").session(session).param("userId", userId)
            .header(ParamConstants.X_AUTHENTICATED_TOKEN, token))
            .andDo(print()).andExpect(status().isOk()).andReturn();
    }

}
