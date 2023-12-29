package com.cern.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cern.mapper.RoleMenuMapper;
import com.cern.domain.entity.RoleMenu;
import com.cern.service.RoleMenuService;
import org.springframework.stereotype.Service;

/**
 * 角色和菜单关联表(RoleMenu)表服务实现类
 *
 * @author makejava
 * @since 2023-12-29 22:53:21
 */
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {

}