package com.cern.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cern.domain.entity.Article;
import org.springframework.stereotype.Repository;

/**
 * 文章表(Article)表数据库访问层
 *
 * @author makejava
 * @since 2023-12-16 21:29:48
 */
@Repository
public interface ArticleMapper extends BaseMapper<Article> {

}

