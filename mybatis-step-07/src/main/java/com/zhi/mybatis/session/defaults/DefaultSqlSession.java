package com.zhi.mybatis.session.defaults;


import com.zhi.mybatis.executor.Executor;
import com.zhi.mybatis.mapping.MappedStatement;
import com.zhi.mybatis.session.Configuration;
import com.zhi.mybatis.session.ResultHandler;
import com.zhi.mybatis.session.SqlSession;

import java.util.List;

/**
 * @author zhijianhao
 * @created by zhijianhao on 2023/3/22-22:28
 */
public class DefaultSqlSession implements SqlSession {

    private Configuration configuration;

    private Executor executor;

    public DefaultSqlSession(Configuration configuration, Executor executor) {
        this.configuration = configuration;
        this.executor = executor;
    }

    @Override
    public <T> T selectOne(String statement) {
        return this.selectOne(statement, null);
    }

    @Override
    public <T> T selectOne(String statement, Object parameter) {
        MappedStatement mappedStatement = configuration.getMappedStatement(statement);
        List<T> list = executor.query(mappedStatement, parameter, (ResultHandler) Executor.NO_RESULT_HANDLER, mappedStatement.getBoundSql());
        return list.get(0);
    }

    @Override
    public Configuration getConfiguration() {
        return configuration;
    }

    @Override
    public <T> T getMapper(Class<T> type) {
        return configuration.getMapper(type, this);
    }
}
