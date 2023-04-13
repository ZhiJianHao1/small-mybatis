package com.zhi.mybatis.builder;

import com.zhi.mybatis.mapping.BoundSql;
import com.zhi.mybatis.mapping.ParameterMapping;
import com.zhi.mybatis.mapping.SqlSource;
import com.zhi.mybatis.session.Configuration;

import java.util.List;

/**
 * 静态sql源码
 * @author zhijianhao
 */
public class StaticSqlSource implements SqlSource {

    private String sql;

    private List<ParameterMapping> parameterMappings;

    private Configuration configuration;

    public StaticSqlSource(Configuration configuration, String sql) {
        this(configuration, sql, null);
    }

    public StaticSqlSource(Configuration configuration, String sql, List<ParameterMapping> parameterMappings) {
        this.sql = sql;
        this.parameterMappings = parameterMappings;
        this.configuration = configuration;
    }

    @Override
    public BoundSql getBoundSql(Object parameterObject) {
        return new BoundSql(configuration, sql, parameterMappings, parameterObject);
    }
}
