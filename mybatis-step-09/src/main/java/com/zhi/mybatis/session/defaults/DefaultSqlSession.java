package com.zhi.mybatis.session.defaults;


import com.alibaba.fastjson.JSON;
import com.zhi.mybatis.executor.Executor;
import com.zhi.mybatis.mapping.MappedStatement;
import com.zhi.mybatis.session.Configuration;
import com.zhi.mybatis.session.ResultHandler;
import com.zhi.mybatis.session.RowBounds;
import com.zhi.mybatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 默认SqlSession实现
 * @author zhijianhao
 * @created by zhijianhao on 2023/3/22-22:28
 */
public class DefaultSqlSession implements SqlSession {
    private Logger logger = LoggerFactory.getLogger(DefaultSqlSession.class);
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
        logger.info("执行查询 statement: {} parameter: {}", statement, JSON.toJSONString(parameter));
        MappedStatement mappedStatement = configuration.getMappedStatement(statement);
        List<T> list = executor.query(mappedStatement, parameter, RowBounds.DEFAULT, (ResultHandler) Executor.NO_RESULT_HANDLER, mappedStatement.getSqlSource().getBoundSql(parameter));
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
