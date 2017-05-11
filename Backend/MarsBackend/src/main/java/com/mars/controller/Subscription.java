package com.mars.controller;

import com.mars.model.UserInfo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.*;

/**
 * Created by tachen on 2017/5/12.
 */
@RestController
@RequestMapping(path = "/private/subscription")
public class Subscription {
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
    String addNewUser(@RequestHeader(value="X-Authenticated-Token") String token) {

        return "It is ok : "+ token;

    }
}
