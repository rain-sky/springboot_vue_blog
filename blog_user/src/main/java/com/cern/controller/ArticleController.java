package com.cern.controller;


import com.cern.annotation.SystemLog;
import com.cern.domain.ResponseResult;
import com.cern.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
@Api(tags = "系统：文章接口")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/list")
    @ApiOperation("获取所有文章")
    @SystemLog("获取所有文章")
    public ResponseResult getArt(){
        return articleService.getArticleList();
    }


    @GetMapping("/hotArticleList")
    @ApiOperation("获取热门文章列表")
    //@SystemLog("获取热门文章列表")
    public ResponseResult hotArticleList(){
        return articleService.hotArticleList();
    }

    @GetMapping("/articleList")
    @ApiOperation("分页查询文章列表")
    //@SystemLog("分页查询文章列表")
    public ResponseResult articleList(Integer pageNum,Integer pageSize,Long categoryId){
        return articleService.articleList(pageNum,pageSize,categoryId);
    }

    @GetMapping("/{id}")
    @ApiOperation("根据文章id获取详情")
    //@SystemLog("根据文章id获取详情")
    public ResponseResult getArticleDetail(@PathVariable("id") Long id) {
        return articleService.getArticleDetail(id);
    }

    @PutMapping("updateViewCount/{id}")
    @ApiOperation("更新文章浏览量")
    //@SystemLog("更新文章浏览量")
    public ResponseResult updateViewCount(@PathVariable("id") Long id){
        return articleService.updateViewCount(id);
    }

}
