package com.cern.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
// 分页返回传输对象
public class PageVo {

    // 数据
    private List rows;
    // 总数
    private Long total;

}