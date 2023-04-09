package com.zhi.mybatis.executor;

import com.zhi.mybatis.executor.resultset.ResultSetHandler;
import com.zhi.mybatis.mapping.BoundSql;
import com.zhi.mybatis.mapping.MappedStatement;
import com.zhi.mybatis.session.ResultHandler;
import com.zhi.mybatis.transaction.Transaction;

import java.sql.SQLException;
import java.util.List;

/**
 * 执行器
 * @author zhijianhao
 */
public interface Executor {

    ResultSetHandler NO_RESULT_HANDLER = null;

    <E> List<E> query(MappedStatement ms, Object parameter, ResultHandler resultHandler, BoundSql boundSql);

    Transaction getTransaction();

    void commit(boolean required) throws SQLException;

    void rollback(boolean required) throws SQLException;

    void close(boolean forceRollback);
}
