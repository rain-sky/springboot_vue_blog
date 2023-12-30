package com.cern.controller;


import com.cern.domain.ResponseResult;
import com.cern.domain.dto.AddArticleDto;
import com.cern.domain.entity.Article;
import com.cern.domain.vo.ArticleByIdVo;
import com.cern.domain.vo.PageVo;
import com.cern.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content/article")
@Api(tags = "系统-后台：文章接口")
public class ArticleController {


    @Autowired
    private ArticleService articleService;

    @PostMapping
    @ApiOperation("后台：新增文章")
    public ResponseResult addArticle(@RequestBody AddArticleDto articleDto){
        return articleService.add(articleDto);
    }

    @GetMapping("/list")
    @ApiOperation("后台：获取文章列表")
    public ResponseResult list(Article article, Integer pageNum, Integer pageSize){
        PageVo pageVo = articleService.selectArticlePage(article,pageNum,pageSize);
        return ResponseResult.okResult(pageVo);
    }

    @GetMapping(value = "/{id}")
    @ApiOperation("后台：查询文章")
    //①先查询根据文章id查询对应的文章
    public ResponseResult getInfo(@PathVariable(value = "id")Long id){
        ArticleByIdVo article = articleService.getInfo(id);
        return ResponseResult.okResult(article);
    }

    @PutMapping
    @ApiOperation("后台：修改文章")
    //②然后才是修改文章
    public ResponseResult edit(@RequestBody AddArticleDto article){
        articleService.edit(article);
        return ResponseResult.okResult();
    }

    //---------------------------根据文章id来删除文章-------------------------
    @DeleteMapping("/{id}")
    @ApiOperation("后台：删除文章")
    public ResponseResult delete(@PathVariable Long id){
        articleService.removeById(id);
        return ResponseResult.okResult();
    }

}
