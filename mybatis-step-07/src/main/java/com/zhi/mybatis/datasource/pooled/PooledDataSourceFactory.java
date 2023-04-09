package com.zhi.mybatis.datasource.pooled;

import com.zhi.mybatis.datasource.unpooled.UnpooledDataSourceFactory;

/**
 * @author zhijianhao
 * @created by zhijianhao on 2023/3/26-21:14
 */
public class PooledDataSourceFactory extends UnpooledDataSourceFactory {
    public PooledDataSourceFactory() {
        this.dataSource = new PooledDataSource();
    }
}
