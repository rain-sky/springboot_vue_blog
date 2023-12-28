package com.cern.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cern.domain.entity.Comment;
import org.springframework.stereotype.Repository;

/**
 * 评论表(Comment)表数据库访问层
 *
 * @author makejava
 * @since 2023-12-16 22:08:12
 */
@Repository
public interface CommentMapper extends BaseMapper<Comment> {

}

