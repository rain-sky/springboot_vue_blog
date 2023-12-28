package com.cern.service.impl;

import com.cern.domain.LoginUser;
import com.cern.domain.ResponseResult;
import com.cern.domain.entity.User;
import com.cern.domain.vo.BlogUserLoginVo;
import com.cern.domain.vo.UserInfoVo;
import com.cern.service.BlogLoginService;
import com.cern.utils.BeanCopyUtils;
import com.cern.utils.JwtUtil;
import com.cern.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 前台登录接口
 * @author 35238
 * @date 2023/7/22 0022 21:39
 */
@Service
public class BlogLoginServiceImpl implements BlogLoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult login(User user) {
        //封装登录的用户名和密码
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //上面那一行会得到所有的认证用户信息authenticate。然后下一行需要判断用户认证是否通过，如果authenticate的值是null，就说明认证没有通过
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("用户名或密码错误");
        }
        //获取userId
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        // 以userId生成token
        String jwt = JwtUtil.createJWT(userId);

        redisCache.setCacheObject("bloglogin:"+userId,loginUser);

        //把User转化为UserInfoVo，再放入vo对象的第二个参数
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        BlogUserLoginVo vo = new BlogUserLoginVo(jwt,userInfoVo);
        //封装响应返回
        return ResponseResult.okResult(vo);
    }

    @Override
    public ResponseResult logout() {
        LoginUser loginUser = (LoginUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = loginUser.getUser().getId();
        redisCache.deleteObject("bloglogin:"+userId);
        return ResponseResult.okResult();
    }
}