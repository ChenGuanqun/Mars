package com.mars.controller;

import com.mars.model.UserInfo;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Created by tachen on 5/10/2017.
 */
@RestController
@RequestMapping(path = "/public/session")
public class SessionController {

    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value = "User Admin Operations", notes = "Create a new user")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success", response = UserInfo.class),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found"),
        @ApiResponse(code = 500, message = "Failure") })
    public
    @ResponseBody
    String addNewUser(@RequestHeader(value="USER-NAME") String userName, @RequestHeader(value="PASSWORD") String password) {

        return "good";

    }
}
