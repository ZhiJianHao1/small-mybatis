package com.zhi.mybatis.binding;

import com.zhi.mybatis.session.SqlSession;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 映射器代理工厂🏭
 * @author zhijianhao
 * @created by zhijianhao on 2023/3/21-23:03
 */
public class MapperProxyFactory<T> {
    private final Class<T> mapperInterface;

    private Map<Method, MapperMethod> methodCache = new ConcurrentHashMap<>();

    public MapperProxyFactory(Class<T> mapperInterface) {
        this.mapperInterface = mapperInterface;
    }

    public  Map<Method, MapperMethod> getMethodCache() {
        return methodCache;
    }

    /**
     * 使用动态代理来实现 MyBatis 的 Mapper 接口
     * @param sqlSession
     * @return
     */
    @SuppressWarnings("unchecked")
    public T newInterface(SqlSession sqlSession) {
        // 处理对 Mapper 接口的方法调用，并将它们转发到底层的 SqlSession。
        final MapperProxy<T> mapperProxy = new MapperProxy<>(sqlSession, mapperInterface, methodCache);
        // 创建一个新的动态代理实例
        return (T) Proxy.newProxyInstance(mapperProxy.getClass().getClassLoader(), new Class[]{mapperInterface}, mapperProxy);
    }

}
