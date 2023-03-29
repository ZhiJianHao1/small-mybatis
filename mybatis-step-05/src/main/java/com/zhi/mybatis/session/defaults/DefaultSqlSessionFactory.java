package com.zhi.mybatis.session.defaults;

import com.zhi.mybatis.session.Configuration;
import com.zhi.mybatis.session.SqlSession;
import com.zhi.mybatis.session.SqlSessionFactory;

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
        return new DefaultSqlSession(configuration);
    }
}
