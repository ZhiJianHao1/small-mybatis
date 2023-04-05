package com.zhi.mybatis.datasource.pooled;

import com.zhi.mybatis.datasource.unpooled.UnpooledDataSourceFactory;

import javax.sql.DataSource;

/**
 * @author zhijianhao
 * @created by zhijianhao on 2023/3/26-21:14
 */
public class PooledDataSourceFactory extends UnpooledDataSourceFactory {
    /**
     * 有池化继承无池化
     * xmlConfigBuilder 在解析环境时，进行设置数据源
     * xmlConfigBuilder 要获取数据源 进行环境配置
     * @return DataSource
     */
    @Override
    public DataSource getDataSource() {
        PooledDataSource pooledDataSource = new PooledDataSource();
        pooledDataSource.setDriver(properties.getProperty("driver"));
        pooledDataSource.setUrl(properties.getProperty("url"));
        pooledDataSource.setUsername(properties.getProperty("username"));
        pooledDataSource.setPassword(properties.getProperty("password"));
        return pooledDataSource;
    }
}
