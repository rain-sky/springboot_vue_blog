package com.cern.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cern.domain.entity.Role;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 角色信息表(Role)表数据库访问层
 *
 * @author makejava
 * @since 2023-12-16 22:09:49
 */
@Repository
public interface RoleMapper extends BaseMapper<Role> {
    //查询普通用户的角色权限
    List<String> selectRoleKeyByOtherUserId(Long userId);
    //修改用户-①根据id查询用户信息
    List<Long> selectRoleIdByUserId(Long userId);
}

