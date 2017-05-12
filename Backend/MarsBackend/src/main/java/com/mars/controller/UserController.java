package com.mars.controller;

import com.mars.common.ParamConstants;
import com.mars.dao.entity.User;
import com.mars.exception.MarsException;
import com.mars.model.UserInfo;

import com.mars.service.UserService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.security.SecureRandom;

import javax.servlet.http.HttpSession;

/**
 * Created by tachen on 5/9/2017.
 */
@RestController
@RequestMapping(path = "/api/user")
public class UserController  {

    private static SecureRandom random = new SecureRandom();

    @Autowired
    private HttpSession httpSession;

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value = "User Admin Operations", notes = "Create a new user")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success", response = UserInfo.class),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found"),
        @ApiResponse(code = 500, message = "Failure") })
    public User addNewUser(@RequestBody UserInfo userInfo) throws MarsException {
        return userService.createUser(userInfo);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/session")
    @ApiOperation(value = "Create a user session", notes = "Create a user session")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success", response = UserInfo.class),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found"),
        @ApiResponse(code = 500, message = "Failure") })
    public
    @ResponseBody
    String createSession(@RequestHeader(value = "USER-NAME") String userName, @RequestHeader(value = "PASSWORD") String password)
        throws MarsException
    {
        String token = null;
        if (userService.userLogon(userName, password)) {
            token = (new BigInteger(130, random)).toString(32);
            httpSession.getServletContext().setAttribute(ParamConstants.SESSION_ID, token);
        } else {
            throw new MarsException(HttpStatus.UNAUTHORIZED, "User name and password does not match!");
        }
        return token;
    }
}
