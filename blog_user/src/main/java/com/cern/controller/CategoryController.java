package com.cern.controller;

import com.cern.annotation.SystemLog;
import com.cern.domain.ResponseResult;
import com.cern.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
@Api(tags = "系统：分类接口")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/getCategoryList")
    @ApiOperation("获取有效分类列表")
    @SystemLog("获取有效分类列表")
    public ResponseResult getCategoryList(){
        return categoryService.getCategoryList();
    }


}
