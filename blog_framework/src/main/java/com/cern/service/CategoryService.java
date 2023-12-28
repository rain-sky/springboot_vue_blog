package com.cern.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cern.domain.ResponseResult;
import com.cern.domain.entity.Category;

/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2023-12-16 22:07:28
 */
public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();
}

