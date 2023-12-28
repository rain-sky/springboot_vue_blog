package com.cern.handler.mybatisplus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.cern.utils.SecurityUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * mybatisPlus中用于自动填充表字段的处理器
 * 需要在数据库实体映射类上增加相应的填充策略注解
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    /**
     * 在向表中插入数据时自动填充字段
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        Long userId = null;
        try {
            //获取用户id
            userId = SecurityUtils.getUserId();
        } catch (Exception e) {
            e.printStackTrace();
            userId = -1L;//如果异常了，就说明该用户还没注册，我们就把该用户的userid字段赋值d为-1
        }
        //自动把下面四个字段新增了值。
        this.setFieldValByName("createTime", new Date(), metaObject);
        this.setFieldValByName("createBy",userId , metaObject);
        this.setFieldValByName("updateTime", new Date(), metaObject);
        this.setFieldValByName("updateBy", userId, metaObject);

    }

    /**
     * 在向表中修改数据时自动填充字段
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime", new Date(), metaObject);
        this.setFieldValByName("updateBy", SecurityUtils.getUserId(), metaObject);
    }
}
