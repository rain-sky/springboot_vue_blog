package com.cern.controller;

import com.cern.annotation.SystemLog;
import com.cern.domain.ResponseResult;
import com.cern.service.LinkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/link")
@Api(tags = "系统：友链接口")
public class LinkController {
    @Autowired
    private LinkService linkService;

    @GetMapping("/getAllLink")
    @ApiOperation("获取友链列表")
    @SystemLog("获取友链列表")
    public ResponseResult getAllLink(){
        return linkService.getAllLink();
    }

}
