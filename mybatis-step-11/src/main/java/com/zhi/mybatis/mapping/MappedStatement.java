package com.zhi.mybatis.mapping;

import com.zhi.mybatis.scripting.LanguageDriver;
import com.zhi.mybatis.session.Configuration;

import java.util.List;

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

    private LanguageDriver lang;

    private List<ResultMap> resultMaps;

    MappedStatement() {
        // constructor disabled
    }

    /**
     * step-10 新增方法
     * @param parameter
     * @return
     */
    public BoundSql getBoundSql(Object parameter) {
        // 调用 SqlSource#getBoundSql
        return sqlSource.getBoundSql(parameter);
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
            mappedStatement.lang = configuration.getDefaultScriptingLanguageInstance();
        }

        public MappedStatement build() {
            assert mappedStatement.configuration != null;
            assert mappedStatement.id != null;
            return mappedStatement;
        }

        public String id() {
            return mappedStatement.id;
        }

        public Builder resultMaps(List<ResultMap> resultMaps) {
            mappedStatement.resultMaps = resultMaps;
            return this;
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

    public LanguageDriver getLang() {
        return lang;
    }

    public List<ResultMap> getResultMaps() {
        return resultMaps;
    }
}
