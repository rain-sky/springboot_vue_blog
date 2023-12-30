package com.cern.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cern.domain.entity.Menu;

import java.util.List;

/**
 * 菜单权限表(Menu)表服务接口
 *
 * @author makejava
 * @since 2023-12-16 22:09:29
 */
public interface MenuService extends IService<Menu> {
    // 查询用户的权限信息
    List<String> selectPermsByUserId(Long id);
    // 查询用户拥有的菜单信息
    List<Menu> selectRouterMenuTreeByUserId(Long userId);
    //查询菜单列表
    List<Menu> selectMenuList(Menu menu);
    //删除菜单-判断是否存在子菜单
    boolean hasChild(Long menuId);
    //修改角色-根据角色id查询对应角色菜单列表树
    List<Long> selectMenuListByRoleId(Long roleId);
}

