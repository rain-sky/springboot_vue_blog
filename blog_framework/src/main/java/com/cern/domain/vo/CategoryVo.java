package com.cern.domain.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryVo {
    private long id;
    private String name;
    // 后台系统中传输新增字段、描述
    private String description;
}
