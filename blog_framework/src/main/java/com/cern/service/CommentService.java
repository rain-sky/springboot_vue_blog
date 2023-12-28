package com.cern.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cern.domain.ResponseResult;
import com.cern.domain.entity.Comment;
/**
 * 评论表(Comment)表服务接口
 *
 * @author makejava
 * @since 2023-12-16 22:08:12
 */
public interface CommentService extends IService<Comment> {
    // 查询某篇文章的评论列表
    ResponseResult commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize);
    //在文章的评论区发送评论
    ResponseResult addComment(Comment comment);
}