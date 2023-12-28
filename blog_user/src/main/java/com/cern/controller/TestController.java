package com.cern.controller;


import com.cern.domain.ResponseResult;
import com.cern.exception.TestException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    // 统一异常处理测试
    @GetMapping("/exception")
    public ResponseResult test(){
        int f = 1;
        if(f == 1){
            throw new TestException(200,"不要轻易的立下flag哦~");
        }
        return ResponseResult.okResult();
    }
}
