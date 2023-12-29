package com.cern.controller;

import com.cern.domain.ResponseResult;
import com.cern.domain.dto.EditTagDto;
import com.cern.domain.dto.TagListDto;
import com.cern.domain.dto.TagVo;
import com.cern.domain.entity.Tag;
import com.cern.service.TagService;
import com.cern.utils.BeanCopyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/content/tag")
@Api(tags = "管理：标签接口")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/list")
    @ApiOperation("获取标签列表")
    public ResponseResult list(Integer pageNum, Integer pageSize, TagListDto tagListDto){
        return tagService.pageTagList(pageNum,pageSize,tagListDto);
    }


    @PostMapping
    @ApiOperation("新增标签")
    public ResponseResult add(@RequestBody TagListDto tagDto){
        Tag tag = BeanCopyUtils.copyBean(tagDto, Tag.class);
        tagService.save(tag);
        return ResponseResult.okResult();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除标签")
    public ResponseResult deleteTagById(@PathVariable("id") Long id){
        tagService.removeById(id);
        return ResponseResult.okResult();
    }


    @GetMapping("/{id}")
    //①根据标签的id来查询标签
    public ResponseResult getInfo(@PathVariable(value = "id")Long id){
        Tag tag = tagService.getById(id);
        return ResponseResult.okResult(tag);
    }

    @PutMapping
    //②根据标签的id来修改标签
    public ResponseResult edit(@RequestBody EditTagDto tagDto){
        Tag tag = BeanCopyUtils.copyBean(tagDto,Tag.class);
        tagService.updateById(tag);
        return ResponseResult.okResult();
    }

    @GetMapping("/listAllTag")
    @ApiOperation("写博文页查询标签列表")
    public ResponseResult listAllTag(){
        List<TagVo> list = tagService.listAllTag();
        return ResponseResult.okResult(list);
    }
}