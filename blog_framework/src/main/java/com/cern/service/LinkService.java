package com.cern.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cern.domain.ResponseResult;
import com.cern.domain.entity.Link;

/**
 * 友链(Link)表服务接口
 *
 * @author makejava
 * @since 2023-12-16 22:08:47
 */
public interface LinkService extends IService<Link> {
    //查询友链
    ResponseResult getAllLink();
}

