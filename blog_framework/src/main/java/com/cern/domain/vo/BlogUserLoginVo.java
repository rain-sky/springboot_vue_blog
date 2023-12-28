package com.cern.domain.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("登录成功传输对象")
public class BlogUserLoginVo {
    private String token;
    private UserInfoVo userInfo;
}
