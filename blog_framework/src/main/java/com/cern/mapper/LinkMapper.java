package com.cern.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cern.domain.entity.Link;
import org.springframework.stereotype.Repository;

/**
 * 友链(Link)表数据库访问层
 *
 * @author makejava
 * @since 2023-12-16 22:08:47
 */
@Repository
public interface LinkMapper extends BaseMapper<Link> {

}

