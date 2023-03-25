package com.zhi.mybatis.session;

import com.zhi.mybatis.binding.MapperRegister;
import com.zhi.mybatis.datasource.druid.DruidDataSourceFactory;
import com.zhi.mybatis.mapping.Environment;
import com.zhi.mybatis.mapping.MappedStatement;
import com.zhi.mybatis.transaction.jdbc.JdbcTransactionFactory;
import com.zhi.mybatis.type.TypeAliasRegistry;

import java.util.HashMap;
import java.util.Map;

/**
 * 配置项
 * @author zhijianhao
 * @created by zhijianhao on 2023/3/23-23:23
 */
public class Configuration {
    /**
     * 环境
     */
    protected Environment environment;

    /**
     * 映射注册机
     */
    protected MapperRegister mapperRegister = new MapperRegister(this);

    /**
     * 映射的语句，存放在Map
     */
    protected final Map<String, MappedStatement> statementMap = new HashMap<>();

    // 类型别名注册机
    protected final TypeAliasRegistry typeAliasRegistry = new TypeAliasRegistry();

    public Configuration() {
        typeAliasRegistry.registerAlias("JDBC", JdbcTransactionFactory.class);
        typeAliasRegistry.registerAlias("DRUID", DruidDataSourceFactory.class);
    }
    public void addMappers(String packageName) {
        mapperRegister.addMappers(packageName);
    }

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

    public TypeAliasRegistry getTypeAliasRegistry() {
        return typeAliasRegistry;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
