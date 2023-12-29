package com.cern.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cern.domain.entity.ArticleTag;
import org.springframework.stereotype.Repository;

/**
 * 文章标签关联表(ArticleTag)表数据库访问层
 *
 * @author makejava
 * @since 2023-12-29 11:12:28
 */
@Repository
public interface ArticleTagMapper extends BaseMapper<ArticleTag> {

}

