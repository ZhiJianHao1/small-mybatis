package com.zhi.mybatis.datasource.unpooled;

import com.zhi.mybatis.datasource.DataSourceFactory;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * 无池化数据源工厂
 * @author zhijianhao
 * @created by zhijianhao on 2023/3/26-17:51
 */
public class UnpooledDataSourceFactory implements DataSourceFactory {

    protected Properties properties;
    @Override
    public void setProperties(Properties props) {
        this.properties = props;
    }

    /**
     * 获取数据源
     *
     * @return
     */
    @Override
    public DataSource getDataSource() {
        UnpooledDataSource unpooledDataSource = new UnpooledDataSource();
        unpooledDataSource.setDriver(properties.getProperty("driver"));
        unpooledDataSource.setUrl(properties.getProperty("url"));
        unpooledDataSource.setUsername(properties.getProperty("username"));
        unpooledDataSource.setPassword(properties.getProperty("password"));
        return unpooledDataSource;
    }
}
