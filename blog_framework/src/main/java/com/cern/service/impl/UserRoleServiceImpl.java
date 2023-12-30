package com.cern.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cern.mapper.UserRoleMapper;
import com.cern.domain.entity.UserRole;
import com.cern.service.UserRoleService;
import org.springframework.stereotype.Service;

/**
 * 用户和角色关联表(UserRole)表服务实现类
 *
 * @author makejava
 * @since 2023-12-30 10:11:39
 */
@Service("userRoleService")
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

}

