package com.zhi.mybatis.executor.resultset;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * 结果集处理器
 * @author zhijianhao
 */
public interface ResultSetHandler {

    <E> List<E> handleResultSets(Statement ps) throws SQLException;
}
