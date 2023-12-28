package com.cern.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cern.constants.SystemConstants;
import com.cern.domain.ResponseResult;
import com.cern.domain.entity.Comment;
import com.cern.domain.vo.CommentVo;
import com.cern.domain.vo.PageVo;
import com.cern.enums.AppHttpCodeEnum;
import com.cern.exception.SystemException;
import com.cern.mapper.CommentMapper;
import com.cern.service.CommentService;
import com.cern.service.UserService;
import com.cern.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 评论表(Comment)表服务实现类
 *
 * @author makejava
 * @since 2023-12-16 22:08:12
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Autowired
    private UserService userService;

    @Override
    public ResponseResult commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize) {
        // 查询出对于文章的所有根评论
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        // 对articleId进行判断，作用是得到指定的文章。如果是文章评论，才会判断articleId，避免友链评论判断articleId时出现空指针
        wrapper.eq(SystemConstants.ARTICLE_COMMENT.equals(commentType),Comment::getArticleId,articleId)
                .eq(Comment::getRootId, SystemConstants.COMMENT_ROOT) // 先查询所有的根评论
                .eq(Comment::getType,commentType); // 区分评论类型

        Page<Comment> page = new Page<>(pageNum,pageSize);
        this.page(page,wrapper);
        //调用下面那个方法。根评论排序
        List<Comment> commentSort = page.getRecords().stream()
                .sorted(Comparator.comparing(Comment::getCreateTime).reversed())
                .collect(Collectors.toList());
        // 转换成传输对象
        List<CommentVo> commentVos = ToCommentList(commentSort);
        // 查询子评论
        for (CommentVo commentVo : commentVos) {
            List<CommentVo> children = getChildren(commentVo.getId());
            commentVo.setChildren(children);
        }
        // 封装成分页查询传输对象
        PageVo pageVo = new PageVo(commentVos, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult addComment(Comment comment) {
        if (!StringUtils.hasText(comment.getContent())){
            throw new SystemException(AppHttpCodeEnum.CONTENT_NOT_NULL);
        }
        this.save(comment);
        return ResponseResult.okResult();
    }

    // 子评论查询方法
    private List<CommentVo> getChildren(Long id){
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Comment::getRootId,id)
                .orderByDesc(Comment::getCreateTime);
        List<Comment> list = this.list(wrapper);
        // 对子评论也应用填充字段方法
        List<CommentVo> commentVos = ToCommentList(list);
        return commentVos;
    }

    // 字段补齐方法
    private List<CommentVo> ToCommentList(List<Comment> list){
        List<CommentVo> commentVos = BeanCopyUtils.copyBeanList(list, CommentVo.class);
        // 为commentVos中每条评论设置username字段
        for (CommentVo commentVo : commentVos) {
            String nickName = userService.getById(commentVo.getCreateBy()).getNickName();
            commentVo.setUsername(nickName);
            // 如果这条评论是子评论，那么同时设置该commentVo的toCommentUserName字段，即回复给谁
            // 如果commentVo.getToCommentUserId()!=-1不为真，说明这条评论有回复对象，即本评论不是根评论
            if (commentVo.getToCommentUserId()!=-1){
                String toCommentUserName = userService.getById(commentVo.getToCommentUserId()).getNickName();
                commentVo.setToCommentUserName(toCommentUserName);
            }
        }
        return commentVos;
    }
}

