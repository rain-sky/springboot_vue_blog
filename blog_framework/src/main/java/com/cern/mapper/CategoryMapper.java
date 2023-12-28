package com.cern.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cern.domain.entity.Category;
import org.springframework.stereotype.Repository;

/**
 * 分类表(Category)表数据库访问层
 *
 * @author makejava
 * @since 2023-12-16 22:07:28
 */
@Repository
public interface CategoryMapper extends BaseMapper<Category> {

}

