package com.cern.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cern.domain.entity.Tag;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.stereotype.Repository;

/**
 * 标签(Tag)表数据库访问层
 *
 * @author makejava
 * @since 2023-12-16 22:16:47
 */
@Repository
public interface TagMapper extends BaseMapper<Tag> {
    int myUpdateById(@Param("id") Long id, @Param("flag") int flag);
}

