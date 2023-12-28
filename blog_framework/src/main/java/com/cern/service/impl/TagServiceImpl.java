package com.cern.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cern.mapper.TagMapper;
import com.cern.domain.entity.Tag;
import com.cern.service.TagService;
import org.springframework.stereotype.Service;

/**
 * 标签(Tag)表服务实现类
 *
 * @author makejava
 * @since 2023-12-16 22:16:47
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

}

