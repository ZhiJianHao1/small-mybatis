package com.zhi.mybatis.mapping;

import com.zhi.mybatis.session.Configuration;

import java.util.Map;

/**
 * 映射语句类
 * @author zhijianhao
 * @created by zhijianhao on 2023/3/23-23:31
 */
public class MappedStatement {
    private Configuration configuration;

    private String id;

    private SqlCommandType sqlCommandType;

    private SqlSource sqlSource;

    Class<?> resultType;

    MappedStatement() {
        // constructor disabled
    }

    public static class Builder {
        private MappedStatement mappedStatement = new MappedStatement();
        // 建造者模式
        public Builder(Configuration configuration, String id, SqlCommandType sqlCommandType, SqlSource sqlSource, Class<?> resultType) {
            // 初始化好的 configuration 配置
            mappedStatement.configuration = configuration;
            // sql id com.zhi.mybatis.test.dao.IUserDao.queryUserInfoById
            mappedStatement.id = id;
            // sql 类型 select insert update insert
            mappedStatement.sqlCommandType = sqlCommandType;
            // 绑定的 sql
            mappedStatement.sqlSource = sqlSource;
            mappedStatement.resultType = resultType;
        }

        public MappedStatement build() {
            assert mappedStatement.configuration != null;
            assert mappedStatement.id != null;
            return mappedStatement;
        }
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public String getId() {
        return id;
    }

    public SqlCommandType getSqlCommandType() {
        return sqlCommandType;
    }

    public SqlSource getSqlSource() {
        return sqlSource;
    }

    public Class<?> getResultType() {
        return resultType;
    }
}
