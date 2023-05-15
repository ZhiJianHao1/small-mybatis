package com.zhi.mybatis.binding;

import cn.hutool.core.lang.ClassScanner;
import com.zhi.mybatis.builder.annotation.MapperAnnotationBuilder;
import com.zhi.mybatis.session.Configuration;
import com.zhi.mybatis.session.SqlSession;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 映射器注册机
 * @author zhijianhao
 * @created by zhijianhao on 2023/3/22-22:13
 */
public class MapperRegister {

    private Configuration configuration;

    public MapperRegister(Configuration configuration) {
        this.configuration = configuration;
    }
    /**
     * 将已经添加的映射器代理添加到Map中
     */
    private final Map<Class<?>, MapperProxyFactory<?>> knownMappers = new HashMap<>();

    /**
     * 获取映射器 IUserDao
     * 动态代理
     * @param type
     * @param sqlSession
     * @return
     * @param <T>
     */
    public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
        // 从knownMappers中获取一个映射器的代理工厂
        final MapperProxyFactory<T> mapperProxyFactory = (MapperProxyFactory<T>) knownMappers.get(type);
        if (mapperProxyFactory == null) {
            throw new RuntimeException("Type " + type + " is not known to the MapperRegistry.");
        }
        try {
            // 实例化
            return mapperProxyFactory.newInterface(sqlSession);
        } catch (Exception e) {
            throw new RuntimeException("Error getting mapper instance. Cause: " + e, e);
        }
    }

    /**
     *
     * @param type IUserDao
     * @param <T>
     */
    public <T> void addMapper(Class<T> type) {
        /**
         * mapper必须是接口才能注册
         */
        if (type.isInterface()) {
            if (hashMap(type)) {
                // 如果重复添加了，报错
                throw new RuntimeException("Type " + type + " is already known to the MapperRegistry.");
            }
            // 将已经添加的映射器代理添加到Map中
            knownMappers.put(type, new MapperProxyFactory<>(type));

            // 解析注解类语句配置
            MapperAnnotationBuilder parser = new MapperAnnotationBuilder(configuration, type);
            parser.parse();
        }
    }

    /**
     * 判断映射器代理Map中是否存在
     * @param type
     * @return
     * @param <T>
     */
    public <T> boolean hashMap(Class<T> type) {
        return knownMappers.containsKey(type);
    }

    public void addMappers(String packageName) {
        Set<Class<?>> mapperSet = ClassScanner.scanPackage(packageName);
        for (Class<?> mapperClass : mapperSet) {
            addMapper(mapperClass);
        }
    }
}
