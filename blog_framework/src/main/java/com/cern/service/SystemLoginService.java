package com.cern.service;

import com.cern.domain.ResponseResult;
import com.cern.domain.entity.User;

// 后台登录接口
public interface SystemLoginService {
    ResponseResult login(User user);
    ResponseResult logout();
}
