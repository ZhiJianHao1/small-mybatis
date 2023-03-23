package com.zhi.mybatis.session;

import com.zhi.mybatis.builder.xml.XmlConfigBuilder;
import com.zhi.mybatis.session.defaults.DefaultSqlSessionFactory;

import java.io.Reader;

/**
 * @author zhijianhao
 * @created by zhijianhao on 2023/3/24-00:18
 */
public class SqlSessionFactoryBuilder {

    public SqlSessionFactory build(Reader reader) {
        XmlConfigBuilder xmlConfigBuilder = new XmlConfigBuilder(reader);
        return build(xmlConfigBuilder.parse());
    }

    public SqlSessionFactory build(Configuration config) {
        return new DefaultSqlSessionFactory(config);
    }
}
