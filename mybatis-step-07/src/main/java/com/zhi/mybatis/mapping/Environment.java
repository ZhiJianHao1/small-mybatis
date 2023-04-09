package com.zhi.mybatis.mapping;

import com.zhi.mybatis.transaction.TransactionFactory;

import javax.sql.DataSource;

/**
 * 环境
 * @author zhijianhao
 * @created by zhijianhao on 2023/3/25-15:33
 */
public final class Environment {
    private final String id;
    // 事务工厂
    private final TransactionFactory transactionFactory;
    // 数据源
    private final DataSource dataSource;

    public Environment(String id, TransactionFactory transactionFactory, DataSource dataSource) {
        this.id = id;
        this.transactionFactory = transactionFactory;
        this.dataSource = dataSource;
    }

    /**
     * xmlConfigBuilder
     */
    public static class Builder {
        private String id;

        private TransactionFactory transactionFactory;

        private DataSource dataSource;

        public Builder(String id) {
            this.id = id;
        }

        /**
         * 构建事务
         * @param transactionFactory
         * @return
         */
        public Builder transactionFactory(TransactionFactory transactionFactory) {
            this.transactionFactory = transactionFactory;
            return this;
        }

        /**
         * 构建数据源
         * @param dataSource
         * @return
         */
        public Builder dataSource(DataSource dataSource) {
            this.dataSource =  dataSource;
            return this;
        }

        public Environment build() {
            return new Environment(this.id, this.transactionFactory, this.dataSource);
        }
    }

    public String getId() {
        return id;
    }

    public TransactionFactory getTransactionFactory() {
        return transactionFactory;
    }

    public DataSource getDataSource() {
        return dataSource;
    }
}
