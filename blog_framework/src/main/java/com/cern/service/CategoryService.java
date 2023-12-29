package com.cern.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cern.domain.ResponseResult;
import com.cern.domain.entity.Category;
import com.cern.domain.vo.CategoryVo;

import java.util.List;

/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2023-12-16 22:07:28
 */
public interface CategoryService extends IService<Category> {

    // 前台查询方法
    ResponseResult getCategoryList();

    // 后台查询方法
    List<CategoryVo> listAllCategory();
}

