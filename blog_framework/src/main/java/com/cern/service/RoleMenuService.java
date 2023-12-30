package com.cern.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cern.domain.entity.RoleMenu;

/**
 * 角色和菜单关联表(RoleMenu)表服务接口
 *
 * @author makejava
 * @since 2023-12-29 22:53:21
 */
public interface RoleMenuService extends IService<RoleMenu> {
    //修改角色-保存修改好的角色信息
    void deleteRoleMenuByRoleId(Long id);
}

