package com.zhi.mybatis.transaction;

import com.zhi.mybatis.session.TransactionIsolationLevel;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * 事务工厂
 * @author zhijianhao
 * @created by zhijianhao on 2023/3/25-15:26
 */
public interface TransactionFactory {
    /**
     * 根据connection 创建事务
     * @param connection
     * @return
     */
    Transaction newTransaction(Connection connection);

    /**
     * 根据数据源和事务隔离级别 创建 Transaction
     * @param dataSource
     * @param level
     * @param isAutoCommit
     * @return
     */
    Transaction newTransaction(DataSource dataSource, TransactionIsolationLevel level, boolean isAutoCommit);
}
