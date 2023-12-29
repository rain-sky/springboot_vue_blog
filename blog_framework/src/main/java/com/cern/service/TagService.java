package com.cern.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cern.domain.ResponseResult;
import com.cern.domain.dto.TagListDto;
import com.cern.domain.dto.TagVo;
import com.cern.domain.entity.Tag;
import com.cern.domain.vo.PageVo;

import java.util.List;

/**
 * 标签(Tag)表服务接口
 *
 * @author makejava
 * @since 2023-12-16 22:16:47
 */
public interface TagService extends IService<Tag> {
    // 查询标签列表
    ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto);

//    //新增标签
//    ResponseResult addTag(TagListDto tagListDto);
//    //删除标签
//    ResponseResult deleteTag(Long id);
//    //修改标签-①根据标签的id来查询标签
//    ResponseResult getLableById(Long id);
//    //修改标签-②根据标签的id来修改标签
//    ResponseResult myUpdateById(TagVo tagVo);

    //写博文-查询文章标签的接口
    List<TagVo> listAllTag();
}

