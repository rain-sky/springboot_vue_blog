package com.cern.service.impl;

import com.cern.domain.LoginUser;
import com.cern.domain.ResponseResult;
import com.cern.domain.entity.User;
import com.cern.enums.AppHttpCodeEnum;
import com.cern.exception.SystemException;
import com.cern.service.SystemLoginService;
import com.cern.utils.JwtUtil;
import com.cern.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;

@Service
public class SystemLoginServiceImpl implements SystemLoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult login(User user) {
        // 认证
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken  =
                new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        // 认证失败返回错误信息
        if (Objects.isNull(authenticate)){
            throw new SystemException(AppHttpCodeEnum.LOGIN_ERROR);
        }
        // 认证成功，将用户信息存入redis
        LoginUser loginUser = (LoginUser)authenticate.getPrincipal();
        String id = loginUser.getUser().getId().toString();
        // 生成jwt
        String jwt = JwtUtil.createJWT(id);

        // 信息存入redis
        redisCache.setCacheObject("login:"+id,loginUser);

        // 返回响应
        HashMap<String, String> map = new HashMap<>();
        map.put("token",jwt);
        return ResponseResult.okResult(map);
    }
}
