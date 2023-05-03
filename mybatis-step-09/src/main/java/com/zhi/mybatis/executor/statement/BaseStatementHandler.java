package com.zhi.mybatis.executor.statement;

import com.zhi.mybatis.executor.Executor;
import com.zhi.mybatis.executor.parameter.ParameterHandler;
import com.zhi.mybatis.executor.resultset.ResultSetHandler;
import com.zhi.mybatis.mapping.BoundSql;
import com.zhi.mybatis.mapping.MappedStatement;
import com.zhi.mybatis.session.Configuration;
import com.zhi.mybatis.session.ResultHandler;
import com.zhi.mybatis.session.RowBounds;

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
    protected final ParameterHandler parameterHandler;

    protected final RowBounds rowBounds;
    protected final BoundSql boundSql;

    public BaseStatementHandler(Executor executor, MappedStatement mappedStatement, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) {
        this.configuration = mappedStatement.getConfiguration();
        this.executor = executor;
        this.mappedStatement = mappedStatement;
        this.rowBounds = rowBounds;
        this.boundSql = boundSql;

        this.parameter = parameter;
        this.parameterHandler = configuration.newParameterHandler(mappedStatement, parameter, boundSql);
        this.resultSetHandler = configuration.newResultSetHandler(executor, mappedStatement, rowBounds, resultHandler, boundSql);
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
