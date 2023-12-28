package com.cern.handler.exception;

import com.cern.domain.ResponseResult;
import com.cern.enums.AppHttpCodeEnum;
import com.cern.exception.SystemException;
import com.cern.exception.TestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// 全局异常处理逻辑
// @RestControllerAdvice为@ControllerAdvice,@ResponseBody的组合注解,
// @ResponseBody代表方法返回值会转换为json并且写入响应体
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // SystemException.class表示该方法要处理哪一类异常
    @ExceptionHandler(SystemException.class)
    public ResponseResult systemExceptionHandler(SystemException e){
        //打印异常信息，方便我们追溯问题的原因。{}是占位符，具体值由e决定
        log.error("出现了异常! {}",e);
        //从异常对象中获取提示信息封装，然后返回。ResponseResult是我们写的类
        return ResponseResult.errorResult(e.getCode(),e.getMsg());
    }

    // 测试用异常
    @ExceptionHandler(TestException.class)
    public ResponseResult testExceptionHandler(TestException e){
        return ResponseResult.okResult(e.getCode(),e.getMessage());
    }

    //其它异常交给这里处理
    @ExceptionHandler(Exception.class)
    public ResponseResult exceptionHandler(Exception e){
        //打印异常信息，方便我们追溯问题的原因。{}是占位符，具体值由e决定
        log.error("出现了异常! {}",e);
        //从异常对象中获取提示信息封装，然后返回。ResponseResult、AppHttpCodeEnum是我们写的类
        return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(),e.getMessage());//枚举值是500
    }
}
