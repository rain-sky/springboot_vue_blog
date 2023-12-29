package com.cern.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cern.mapper.ArticleTagMapper;
import com.cern.domain.entity.ArticleTag;
import com.cern.service.ArticleTagService;
import org.springframework.stereotype.Service;

/**
 * 文章标签关联表(ArticleTag)表服务实现类
 *
 * @author makejava
 * @since 2023-12-29 11:12:28
 */
@Service
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements ArticleTagService {

}

