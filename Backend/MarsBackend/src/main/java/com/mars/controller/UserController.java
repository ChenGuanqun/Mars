package com.mars.controller;

import com.mars.dao.entity.User;
import com.mars.model.UserInfo;

import com.mars.model.UserRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * Created by tachen on 5/9/2017.
 */
@RestController
@RequestMapping(path="/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value = "Get Person", notes = "Create a new user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = UserInfo.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")})
    public User addNewUser(@RequestBody UserInfo userInfo) {

        User user = new User();
        user.setName(userInfo.getName());
        user.setEmail(userInfo.getEmail());
        user.setPhone(userInfo.getPhone());
        user.setPassword(userInfo.getPassword());
        userRepository.save(user);
        return user;

    }


}
