package com.cern.filter;

import com.alibaba.fastjson.JSON;
import com.cern.domain.LoginUser;
import com.cern.domain.ResponseResult;
import com.cern.enums.AppHttpCodeEnum;
import com.cern.utils.JwtUtil;
import com.cern.utils.RedisCache;
import com.cern.utils.WebUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;


@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取请求头中的token值，前端通常将token存入cookie中
        String token = request.getHeader("token");
        //判断上面那行有没有拿到token值,没有拿到则放行，此时SecurityContextHolder中将不会被存入认证用户对象
        if(!StringUtils.hasText(token)){
            filterChain.doFilter(request,response);
            return;
        }
        // 获取到token则从token中解析出用户userId，并使用该userId从redis中查询出认证用户对象并存入SecurityContextHolder
        // 这样后续处理可从SecurityContextHolder取出已认证对象
        Claims claims = null;
        try {
            claims = JwtUtil.parseJWT(token);
        } catch (Exception e) {
            //当token超时或token被篡改就会进入下面那行的异常处理
            e.printStackTrace();
            //报异常之后，把异常响应给前端，需要重新登录。
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(response, JSON.toJSONString(result));
            return;
        }
        String userId = claims.getSubject();
        LoginUser loginUser = redisCache.getCacheObject("bloglogin:" + userId);
        //如果在redis获取不到值，说明登录是过期了，需要重新登录
        if(Objects.isNull(loginUser)){
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(response, JSON.toJSONString(result));
            return;
        }
        //把从redis获取到的loginUser，封装成UsernamePasswordAuthenticationToken存入SecurityContextHolder
        //以便后续过滤器能够访问到
        UsernamePasswordAuthenticationToken authenticationToken =
                // 构造方法使用两个参数的代表是未认证的用户，三个参数代表已经认证的用户，具体见源码
                new UsernamePasswordAuthenticationToken(loginUser,null,loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request,response);
    }
}