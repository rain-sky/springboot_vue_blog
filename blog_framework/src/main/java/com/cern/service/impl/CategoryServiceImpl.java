package com.cern.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cern.constants.SystemConstants;
import com.cern.domain.ResponseResult;
import com.cern.domain.entity.Article;
import com.cern.domain.entity.Category;
import com.cern.domain.vo.CategoryVo;
import com.cern.mapper.CategoryMapper;
import com.cern.service.ArticleService;
import com.cern.service.CategoryService;
import com.cern.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 分类表(Category)表服务实现类
 *
 * @author makejava
 * @since 2023-12-16 22:07:28
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private ArticleService articleService;

    @Override
    public ResponseResult getCategoryList() {
        // 查询出所有发布状态的文章
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper();
        wrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> list = articleService.list(wrapper);
        // 使用流式编程得出所有已发布文章的类别集合
        List<Long> collect = list.stream()
                .map(o -> o.getCategoryId())
                .distinct()
                .collect(Collectors.toList());
        // 查询在类别集合中的分类
        List<Category> categories = this.listByIds(collect);
        // 过滤被停用的分类
        List<Category> afterFilterCategories = categories.stream()
                .filter(o -> o.getStatus() != SystemConstants.CATEGORY_STATUS_NORMAL)
                .collect(Collectors.toList());
        // 转成Vo传输并返回
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(afterFilterCategories, CategoryVo.class);
        return ResponseResult.okResult(categoryVos);
    }

    // 写博文页面查询分类列表
    @Override
    public List<CategoryVo> listAllCategory() {
        // 查询正常分类集合
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Category::getStatus, SystemConstants.NORMAL);
        List<Category> list = this.list(wrapper);
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(list, CategoryVo.class);
        return categoryVos;
    }
}

