package com.cern.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cern.constants.SystemConstants;
import com.cern.domain.ResponseResult;
import com.cern.domain.dto.AddArticleDto;
import com.cern.domain.entity.Article;
import com.cern.domain.entity.ArticleTag;
import com.cern.domain.vo.ArticleDetailVo;
import com.cern.domain.vo.ArticleListVo;
import com.cern.domain.vo.HotArticleVo;
import com.cern.domain.vo.PageVo;
import com.cern.enums.AppHttpCodeEnum;
import com.cern.mapper.ArticleMapper;
import com.cern.service.ArticleService;
import com.cern.service.ArticleTagService;
import com.cern.service.CategoryService;
import com.cern.utils.BeanCopyUtils;
import com.cern.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 文章表(Article)表服务实现类
 *
 * @author makejava
 * @since 2023-12-16 21:29:49
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {



    @Autowired
    @Lazy
    private CategoryService categoryService;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ArticleTagService articleTagService;


    // 测试接口，返回所有文章
    @Override
    public ResponseResult getArticleList() {
        return new ResponseResult(AppHttpCodeEnum.SUCCESS.getCode(),
                AppHttpCodeEnum.SUCCESS.getMsg(),this.list());
    }

    // 返回热门文章十篇
    @Override
    public ResponseResult hotArticleList() {
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        wrapper.orderByDesc(Article::getViewCount);
        Page<Article> page = this.page(new Page<>(SystemConstants.ARTICLE_STATUS_CURRENT,
                SystemConstants.ARTICLE_STATUS_SIZE),wrapper);
        List<Article> records = page.getRecords();
        // vo优化，bean拷贝，传统循环方式
        // ArrayList<HotArticleVo> hotArticleVos = new ArrayList<>();
        // for (Article article:records){
        //      HotArticleVo hotArticleVo = new HotArticleVo();
        //      BeanUtils.copyProperties(article,hotArticleVo);
        //      hotArticleVos.add(hotArticleVo);
        //    }
        // 使用流式编程方式
        List<HotArticleVo> hotArticleVos = BeanCopyUtils.copyBeanList(records, HotArticleVo.class);
        for (HotArticleVo hotArticleVo : hotArticleVos) {
            Integer viewCount = redisCache.getCacheMapValue("article:viewCount", hotArticleVo.getId().toString());
            hotArticleVo.setViewCount(viewCount.longValue());
        }
        return ResponseResult.okResult(hotArticleVos);
    }

    // 分页查询文章列表
    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Objects.nonNull(categoryId)&&categoryId>0,Article::getCategoryId,categoryId)
                .eq(Article::getStatus,SystemConstants.ARTICLE_STATUS_NORMAL)
                .orderByDesc(Article::getIsTop);
        Page<Article> page = new Page<>(pageNum,pageSize);
        // 查询出来的数据就封装在传入的page参数所指的对象中
        this.page(page, wrapper);
        List<Article> records = page.getRecords();
        // 转换成Vo对象并封装返回
        // TODO ArticleListVo中的categoryName还未被赋值,如下操作对其赋值
        records.stream()
                .forEach(article -> article.setCategoryName(categoryService.getById(article.getCategoryId()).getName()));
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(records, ArticleListVo.class);
        for (ArticleListVo articleListVo : articleListVos) {
            Integer viewCount = redisCache.getCacheMapValue("article:viewCount", articleListVo.getId().toString());
            articleListVo.setViewCount(viewCount.longValue());
        }
        PageVo pageVo = new PageVo(articleListVos, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    // 根据id获取文章详情
    @Override
    public ResponseResult getArticleDetail(Long id) {
        Article article = this.getById(id);
        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);
        articleDetailVo.setCategoryName(categoryService.getById(article.getCategoryId()).getName());
        Integer viewCount = redisCache.getCacheMapValue("article:viewCount", id.toString());
        articleDetailVo.setViewCount(viewCount.longValue());
        return ResponseResult.okResult(articleDetailVo);
    }

    @Override
    public ResponseResult updateViewCount(Long id) {
        //更新redis中的浏览量，对应文章id的viewCount浏览量。
        //用户每从mysql根据文章id查询一次浏览量，那么redis的浏览量就增加1
        redisCache.incrementCacheMapValue("article:viewCount",id.toString(),1);
        return ResponseResult.okResult();
    }
    /**
     * *****************************************系统后台方法*************************************
     */
    @Override
    @Transactional //声明式事务
    public ResponseResult add(AddArticleDto articleDto) {
        // 保存文章信息
        Article article = BeanCopyUtils.copyBean(articleDto, Article.class);
        save(article);
        // 保存文章标签映射关系
        List<Long> tags = articleDto.getTags();
        List<ArticleTag> collect = tags.stream()
                .map(tag -> new ArticleTag(article.getId(), tag))
                .collect(Collectors.toList());
        articleTagService.saveBatch(collect);
        return ResponseResult.okResult();
    }
}


