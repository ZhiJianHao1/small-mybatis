package com.zhi.session.defaults;

import com.zhi.binding.MapperRegister;
import com.zhi.session.SqlSession;

/**
 * @author zhijianhao
 * @created by zhijianhao on 2023/3/22-22:28
 */
public class DefaultSqlSession implements SqlSession {

    private MapperRegister mapperRegister;

    public DefaultSqlSession(MapperRegister mapperRegister) {
        this.mapperRegister = mapperRegister;
    }

    @Override
    public <T> T selectOne(String statement) {
        return (T) ("你的操作被代理了！" + statement);
    }

    @Override
    public <T> T selectOne(String statement, Object parameter) {
        return (T) ("你的操作被代理了！" + statement + "入参：" + parameter);
    }

    @Override
    public <T> T getMapper(Class<T> type) {
        return mapperRegister.getMapper(type, this);
    }
}
