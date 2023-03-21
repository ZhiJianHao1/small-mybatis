package com.zhi.mybatis.binding;

import java.lang.reflect.Proxy;
import java.util.Map;

/**
 * @author zhijianhao
 * @created by zhijianhao on 2023/3/21-23:03
 */
public class MapperProxyFactory<T> {
    private final Class<T> mapperInterface;

    public MapperProxyFactory(Class<T> mapperInterface) {
        this.mapperInterface = mapperInterface;
    }

    public T newInterface(Map<String, String> sqlSession) {
        final MapperProxy<T> mapperProxy = new MapperProxy<>(sqlSession, mapperInterface);
        return (T) Proxy.newProxyInstance(mapperProxy.getClass().getClassLoader(), new Class[]{mapperInterface}, mapperProxy);

    }

}
