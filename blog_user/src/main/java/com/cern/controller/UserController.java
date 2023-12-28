package com.cern.controller;

import com.cern.annotation.SystemLog;
import com.cern.domain.ResponseResult;
import com.cern.domain.entity.User;
import com.cern.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Api(tags = "系统：用户信息接口")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/userInfo")
    @ApiOperation("获取用户信息")
    @SystemLog("获取用户信息")
    public ResponseResult userInfo(){
        return userService.userInfo();
    }

    @PutMapping("userInfo")
    @ApiOperation("更新用户信息")
    @SystemLog("更新用户信息")
    public ResponseResult  updateUserInfo(@RequestBody User user){
        //更新个人信息
        return userService.updateUserInfo(user);
    }

    @PostMapping("/register")
    @ApiOperation("用户注册")
    @SystemLog("用户注册")
    public ResponseResult register(@RequestBody User user){
        //注册功能
        return userService.register(user);
    }
}
