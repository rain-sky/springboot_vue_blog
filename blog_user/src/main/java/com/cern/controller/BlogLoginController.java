package com.cern.controller;

import com.cern.annotation.SystemLog;
import com.cern.domain.ResponseResult;
import com.cern.domain.entity.User;
import com.cern.enums.AppHttpCodeEnum;
import com.cern.exception.SystemException;
import com.cern.service.BlogLoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "系统：登录接口")
public class BlogLoginController {

    @Autowired
    //BlogLoginService是我们在service目录写的接口
    private BlogLoginService blogLoginService;

    @PostMapping("/login")
    @ApiOperation("登录接口")
    @SystemLog("登录接口")
    public ResponseResult login(@RequestBody User user){
        if (!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return blogLoginService.login(user);
    }

    @PostMapping("/logout")
    @ApiOperation("登出接口")
    @SystemLog("登出接口")
    public ResponseResult logout(){
        return blogLoginService.logout();
    }

}
