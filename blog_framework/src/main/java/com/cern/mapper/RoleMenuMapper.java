package com.cern.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cern.domain.entity.RoleMenu;
import org.springframework.stereotype.Repository;

/**
 * 角色和菜单关联表(RoleMenu)表数据库访问层
 *
 * @author makejava
 * @since 2023-12-29 22:53:21
 */
@Repository
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {

}

