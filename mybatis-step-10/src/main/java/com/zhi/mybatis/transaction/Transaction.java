package com.zhi.mybatis.transaction;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 事务接口
 * Connection、commit、rollback、close
 * @author zhijianhao
 * @created by zhijianhao on 2023/3/25-15:23
 */
public interface Transaction {
    Connection getConnection() throws SQLException;

    void commit() throws SQLException;

    void rollback() throws SQLException;

    void close() throws SQLException;
}
