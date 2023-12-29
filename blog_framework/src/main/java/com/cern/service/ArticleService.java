package com.cern.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cern.domain.ResponseResult;
import com.cern.domain.dto.AddArticleDto;
import com.cern.domain.entity.Article;
import com.cern.domain.vo.ArticleByIdVo;
import com.cern.domain.vo.PageVo;

/**
 * 文章表(Article)表服务接口
 *
 * @author makejava
 * @since 2023-12-16 21:29:49
 */
public interface ArticleService extends IService<Article> {
    // 测试接口
    ResponseResult getArticleList();
    // 生产接口
    ResponseResult hotArticleList();
    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);
    //根据id查询文章详情
    ResponseResult getArticleDetail(Long id);
    //根据文章id增加浏览量
    ResponseResult updateViewCount(Long id);

    // 后台新增博文方法
    ResponseResult add(AddArticleDto articleDto);

    // 后台(文章管理)-分页查询文章
    PageVo selectArticlePage(Article article, Integer pageNum, Integer pageSize);

    //修改文章-①根据文章id查询对应的文章
    ArticleByIdVo getInfo(Long id);

    //修改文章-②然后才是修改文章
    void edit(AddArticleDto article);
}