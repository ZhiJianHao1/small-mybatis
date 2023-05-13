package com.zhi.mybatis.datasource;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * 数据源工厂
 * @author zhijianhao
 * @created by zhijianhao on 2023/3/25-16:08
 */
public interface DataSourceFactory {
    void setProperties(Properties props);

    DataSource getDataSource();
}
