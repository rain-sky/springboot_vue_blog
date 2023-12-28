package com.cern.utils;

import com.cern.domain.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

// 用于获取已经登录的用户信息的工具类
public class SecurityUtils {

    /**
     * 获取登录用户实体类
     */
    public static LoginUser getLoginUser() {
        return (LoginUser) getAuthentication().getPrincipal();
    }

    /**
     * 获取认证对象Authentication
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 指定userid为1的用户就是网站管理员
     */
    public static Boolean isAdmin(){
        Long id = getLoginUser().getUser().getId();
        return id != null && 1L == id;
    }

    /**
     * 获取用户的userid
     **/
    public static Long getUserId() {
        return getLoginUser().getUser().getId();
    }
}