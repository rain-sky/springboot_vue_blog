package com.cern.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cern.domain.ResponseResult;
import com.cern.domain.entity.Role;

import java.util.List;

/**
 * 角色信息表(Role)表服务接口
 *
 * @author makejava
 * @since 2023-12-16 22:09:49
 */
public interface RoleService extends IService<Role> {
    // 查询用户的角色集合
    List<String> selectRoleKeyByUserId(Long id);
    //查询角色列表
    ResponseResult selectRolePage(Role role, Integer pageNum, Integer pageSize);
    //新增角色
    void insertRole(Role role);
    //修改角色-保存修改好的角色信息
    void updateRole(Role role);
    //新增用户-①查询角色列表接口
    List<Role> selectRoleAll();
    //修改用户-①根据id查询用户信息
    List<Long> selectRoleIdByUserId(Long userId);
}

