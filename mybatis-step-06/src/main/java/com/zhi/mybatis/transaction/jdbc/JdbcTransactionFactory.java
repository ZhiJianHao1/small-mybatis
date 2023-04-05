package com.zhi.mybatis.transaction.jdbc;

import com.zhi.mybatis.session.TransactionIsolationLevel;
import com.zhi.mybatis.transaction.Transaction;
import com.zhi.mybatis.transaction.TransactionFactory;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * @author zhijianhao
 * @created by zhijianhao on 2023/3/25-15:45
 */
public class JdbcTransactionFactory implements TransactionFactory {

    @Override
    public Transaction newTransaction(Connection connection) {
        return new JdbcTransaction(connection);
    }

    @Override
    public Transaction newTransaction(DataSource dataSource, TransactionIsolationLevel level, boolean isAutoCommit) {
        return new JdbcTransaction(dataSource, level, isAutoCommit);
    }
}
