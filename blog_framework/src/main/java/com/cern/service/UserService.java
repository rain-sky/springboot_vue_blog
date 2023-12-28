package com.cern.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cern.domain.ResponseResult;
import com.cern.domain.entity.User;

/**
 * 用户表(User)表服务接口
 *
 * @author makejava
 * @since 2023-12-16 22:57:11
 */
public interface UserService extends IService<User> {
    // 查询用户信息
    ResponseResult userInfo();
    // 更新用户信息
    ResponseResult updateUserInfo(User user);
    // 用户注册功能
    ResponseResult register(User user);
}

