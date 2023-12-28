package com.cern.aspect;

import com.alibaba.fastjson.JSON;
import com.cern.annotation.SystemLog;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Component
@Aspect
@Slf4j
public class LogAspect {

    // 自定义切入点，标注注解SystemLog的方法为目标切入点
    @Pointcut("@annotation(com.cern.annotation.SystemLog)")
    public void pointcut(){}

    // 用于增强的通知
    @Around("pointcut()")
    @Synchronized // 为了防止并发请求下不同请求的日志彼此交替错乱，暂时先加上悲观锁
    public Object printLog(ProceedingJoinPoint proceedingJoinPoint){

        // 方法执行前操作,输出请求详细信息
        beforeHandler(proceedingJoinPoint);
        Object proceed = null;
        try {
            // 方法的返回值可以直接获取
            proceed = proceedingJoinPoint.proceed();
            // 方法执行后操作,输出请求响应信息
            afterHandler(proceed);
        } catch (Throwable throwable) {
            // 方法执行期间出现的异常在这里处理
            throwable.printStackTrace();
        }finally {
            log.info("=======================end=======================" + System.lineSeparator());
        }
        return proceed;
    }

    public void  beforeHandler(ProceedingJoinPoint joinPoint){

        ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        log.info("======================Start======================");
        log.info("请求URL   : {}",request.getRequestURL());
        // 打印描述信息，例如获取UserController类的updateUserInfo方法上一行的@mySystemlog注解的描述信息
        log.info("接口描述   : {}",this.getAnnotation(joinPoint).value());
        // 打印 Http method
        log.info("请求方式   : {}",request.getMethod());
        // 打印调用 controller 的全路径(全类名)、方法名
        log.info("请求类名   : {}.{}",joinPoint.getSignature().getDeclaringTypeName(),((MethodSignature) joinPoint.getSignature()).getName());
        // 打印请求的 IP
        log.info("访问IP    : {}",request.getRemoteHost());
        // 打印请求入参。JSON.toJSONString十FastJson提供的工具方法，能把数组转成JSON
        log.info("传入参数   : {}", JSON.toJSONString(joinPoint.getArgs()));

    }

    public void afterHandler(Object ret){
        log.info("返回参数   : {}",JSON.toJSONString(ret));
    }

    public SystemLog getAnnotation(ProceedingJoinPoint joinPoint){
        // 获取方法签名
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        // 获取方法上的注解信息
        SystemLog annotation = (SystemLog)signature.getMethod().getAnnotation(SystemLog.class);
        return annotation;
    }

}
