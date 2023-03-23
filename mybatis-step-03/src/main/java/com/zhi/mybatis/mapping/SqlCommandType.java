package com.zhi.mybatis.mapping;

import org.omg.CORBA.UNKNOWN;

/**
 * @author zhijianhao
 * @created by zhijianhao on 2023/3/23-23:31
 */
public enum SqlCommandType {
    /**
     * 未知
     */
    UNKNOWN,
    /**
     * 插入
     */
    INSERT,
    /**
     * 更新
     */
    UPDATE,
    /**
     * 删除
     */
    DELETE,
    /**
     * 查找
     */
    SELECT;
}
