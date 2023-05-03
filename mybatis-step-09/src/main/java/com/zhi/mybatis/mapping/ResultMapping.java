package com.zhi.mybatis.mapping;

import com.zhi.mybatis.session.Configuration;
import com.zhi.mybatis.type.JdbcType;
import com.zhi.mybatis.type.TypeHandler;

/**
 * TODO
 * 结果映射
 * @author：zhijianhao
 * @date: 2023/4/26
 */
public class ResultMapping {

    private Configuration configuration;

    private String property;

    private String column;

    private Class<?> javaType;

    private JdbcType jdbcType;

    private TypeHandler<?> typeHandler;

    ResultMapping() {

    }

    public static class Builder {
        private ResultMapping resultMapping = new ResultMapping();
    }
}
