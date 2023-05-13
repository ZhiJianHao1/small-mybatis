package com.zhi.mybatis.session;

import com.zhi.mybatis.builder.xml.XmlConfigBuilder;
import com.zhi.mybatis.session.defaults.DefaultSqlSessionFactory;

import java.io.Reader;

/**
 * @author zhijianhao
 * @created by zhijianhao on 2023/3/24-00:18
 */
public class SqlSessionFactoryBuilder {

    /**
     * 创建SqlSessionFactory 用于创建 SqlSession 执行对数据库操作
     * SqlSessionFactory 类似于 JDBC 的 Connection
     * @param reader
     * @return
     */
    public SqlSessionFactory build(Reader reader) {
        // 利用有参构造将解析好的Reader对象传入
        // 在创建对象时初始化XmlConfigBuilder对象的状态
        XmlConfigBuilder xmlConfigBuilder = new XmlConfigBuilder(reader);
        return build(xmlConfigBuilder.parse());
    }

    public SqlSessionFactory build(Configuration config) {
        return new DefaultSqlSessionFactory(config);
    }
}
