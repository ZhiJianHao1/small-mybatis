package com.zhi.mybatis.session.defaults;

import com.zhi.mybatis.executor.Executor;
import com.zhi.mybatis.mapping.Environment;
import com.zhi.mybatis.session.Configuration;
import com.zhi.mybatis.session.SqlSession;
import com.zhi.mybatis.session.SqlSessionFactory;
import com.zhi.mybatis.session.TransactionIsolationLevel;
import com.zhi.mybatis.transaction.Transaction;
import com.zhi.mybatis.transaction.TransactionFactory;

import java.sql.SQLException;

/**
 * @author zhijianhao
 * @created by zhijianhao on 2023/3/22-22:29
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {

    private Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    /**
     * 打开一个sqlSession 可以理解为创建一个sqlSession
     * @return
     */
    @Override
    public SqlSession openSession() {
        Transaction tx = null;
        try {
           final Environment environment = configuration.getEnvironment();
            TransactionFactory transactionFactory = environment.getTransactionFactory();
            tx = transactionFactory.newTransaction(configuration.getEnvironment().getDataSource(), TransactionIsolationLevel.READ_COMMITTED, false);
            // 创建执行器
            Executor executor = configuration.newExecutor(tx);
            // 创建DefaultSqlSession
            return new DefaultSqlSession(configuration, executor);
        } catch (Exception e) {
            try {
                assert tx != null;
                tx.close();
            } catch (SQLException ignore) {
            }
            throw new RuntimeException("Error opening session.  Cause: " + e);
        }
    }
}
