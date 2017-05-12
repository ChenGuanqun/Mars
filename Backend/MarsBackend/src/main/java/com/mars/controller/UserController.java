package com.mars.controller;

import com.mars.common.ParamConstants;
import com.mars.dao.entity.UserEntity;
import com.mars.exception.MarsException;
import com.mars.model.UserInfo;

import com.mars.service.UserService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.security.SecureRandom;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by tachen on 5/9/2017.
 */
@RestController
@RequestMapping(path = "/api/users")
public class UserController  {

    private static SecureRandom random = new SecureRandom();

    @Autowired
    private HttpSession httpSession;

    @Autowired
    private HttpServletResponse servletResponse;

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value = "User Admin Operations", notes = "Create a new user")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success", response = UserEntity.class),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found"),
        @ApiResponse(code = 500, message = "Failure") })
    public UserEntity addNewUser(@RequestBody UserInfo userInfo) throws MarsException {
        return userService.createUser(userInfo);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/session")
    @ApiOperation(value = "Create a user session", notes = "Create a user session")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success", response = UserEntity.class),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found"),
        @ApiResponse(code = 500, message = "Failure") })
    public
    @ResponseBody
    UserEntity createSession(@RequestHeader(value = "USER-NAME") String userName, @RequestHeader(value = "PASSWORD") String password)
        throws MarsException
    {
        UserEntity userEntity = userService.userLogon(userName, password);
        String token = (new BigInteger(130, random)).toString(32);
        httpSession.getServletContext().setAttribute(ParamConstants.SESSION_ID, token);
        servletResponse.setHeader(ParamConstants.X_AUTHENTICATED_TOKEN,token);
        return userEntity;
    }
}
