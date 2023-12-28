package com.cern.service;

import com.cern.domain.ResponseResult;
import com.cern.domain.entity.User;

// 前台登录接口
public interface BlogLoginService {
    ResponseResult login(User user);
    ResponseResult logout();
}
