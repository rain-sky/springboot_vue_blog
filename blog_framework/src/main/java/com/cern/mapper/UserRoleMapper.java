package com.cern.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cern.domain.entity.UserRole;
import org.springframework.stereotype.Repository;

/**
 * 用户和角色关联表(UserRole)表数据库访问层
 *
 * @author makejava
 * @since 2023-12-30 10:11:39
 */
@Repository
public interface UserRoleMapper extends BaseMapper<UserRole> {

}

