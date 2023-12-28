package com.cern.handler;

import com.alibaba.fastjson.JSON;
import com.cern.domain.ResponseResult;
import com.cern.enums.AppHttpCodeEnum;
import com.cern.utils.WebUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 授权异常处理类（替代springSecurity默认处理方式）
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        // 打印异常信息
        accessDeniedException.printStackTrace();
        // 给前端返回信息
        ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NO_OPERATOR_AUTH);
        String json = JSON.toJSONString(result);
        WebUtils.renderString(response,json);
    }
}