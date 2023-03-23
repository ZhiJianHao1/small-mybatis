package com.zhi.mybatis.session;

import com.zhi.mybatis.binding.MapperRegister;
import com.zhi.mybatis.mapping.MappedStatement;

import java.util.HashMap;
import java.util.Map;

/**
 * 配置项
 * @author zhijianhao
 * @created by zhijianhao on 2023/3/23-23:23
 */
public class Configuration {
    /**
     * 映射注册机
     */
    protected MapperRegister mapperRegister = new MapperRegister(this);

    /**
     * 映射的语句，存放在Map
     */
    protected final Map<String, MappedStatement> statementMap = new HashMap<>();

    public void addMapper(Class<?> type) {
        mapperRegister.addMapper(type);
    }

    public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
        return mapperRegister.getMapper(type, sqlSession);
    }

    public boolean hasMapper(Class<?> type) {
        return mapperRegister.hashMap(type);
    }

    public void addMappedStatement(MappedStatement ms) {
        statementMap.put(ms.getId(), ms);
    }

    public MappedStatement getMappedStatement(String id) {
        return statementMap.get(id);
    }
}
