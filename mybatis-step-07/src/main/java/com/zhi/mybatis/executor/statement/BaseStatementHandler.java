package com.zhi.mybatis.executor.statement;

import com.zhi.mybatis.executor.Executor;
import com.zhi.mybatis.executor.resultset.ResultSetHandler;
import com.zhi.mybatis.mapping.BoundSql;
import com.zhi.mybatis.mapping.MappedStatement;
import com.zhi.mybatis.session.Configuration;
import com.zhi.mybatis.session.ResultHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 语句处理器父类
 * @author zhijianhao
 */
public abstract class BaseStatementHandler implements StatementHandler{
    protected final Configuration configuration;
    protected final Executor executor;

    protected final Object parameter;

    protected final MappedStatement mappedStatement;

    protected final ResultSetHandler resultSetHandler;

    protected final BoundSql boundSql;

    public BaseStatementHandler(Executor executor, MappedStatement mappedStatement, Object parameter, ResultHandler resultHandler, BoundSql boundSql) {
        this.configuration = mappedStatement.getConfiguration();
        this.executor = executor;
        this.mappedStatement = mappedStatement;
        this.parameter = parameter;
        this.boundSql = boundSql;

        this.resultSetHandler = configuration.newResultHandler(executor, mappedStatement, boundSql);
    }

    @Override
    public Statement prepare(Connection connection) throws SQLException {
        Statement statement = null;
        try {
            statement = instantiateStatement(connection);
            statement.setQueryTimeout(350);
            statement.setFetchSize(10000);
            return statement;
        } catch (Exception e) {
            throw new RuntimeException("Error preparing statement.  Cause: " + e, e);
        }
    }

    protected abstract Statement instantiateStatement(Connection connection) throws SQLException;
}
