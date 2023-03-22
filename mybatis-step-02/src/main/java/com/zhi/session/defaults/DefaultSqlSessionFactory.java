package com.zhi.session.defaults;

import com.zhi.binding.MapperRegister;
import com.zhi.session.SqlSession;
import com.zhi.session.SqlSessionFactory;

/**
 * @author zhijianhao
 * @created by zhijianhao on 2023/3/22-22:29
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {

    private MapperRegister mapperRegister;

    public DefaultSqlSessionFactory(MapperRegister mapperRegister) {
        this.mapperRegister = mapperRegister;
    }

    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(mapperRegister);
    }
}
