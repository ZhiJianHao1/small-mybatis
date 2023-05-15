package com.zhi.mybatis.mapping;

/**
 * SQL 源码
 * @author zhijianhao
 */
public interface SqlSource {
    BoundSql getBoundSql(Object parameterObject);
}
