package com.cern.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cern.domain.entity.User;
import org.springframework.stereotype.Repository;

/**
 * 用户表(User)表数据库访问层
 *
 * @author makejava
 * @since 2023-12-16 22:57:10
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

}

