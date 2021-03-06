package com.mars.controller;

import com.mars.dao.entity.User;
import com.mars.exception.MarsException;
import com.mars.model.UserInfo;

import com.mars.dao.UserRepository;
import com.mars.service.UserService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by tachen on 5/9/2017.
 */
@RestController
@RequestMapping(path="/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value = "User Admin Operations", notes = "Create a new user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = UserInfo.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")})
    public User addNewUser(@RequestBody UserInfo userInfo) throws MarsException {
        return userService.createUser(userInfo);
    }


}
