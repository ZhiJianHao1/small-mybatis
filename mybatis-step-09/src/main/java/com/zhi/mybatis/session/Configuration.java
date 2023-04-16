package com.zhi.mybatis.session;

import com.zhi.mybatis.binding.MapperRegister;
import com.zhi.mybatis.datasource.druid.DruidDataSourceFactory;
import com.zhi.mybatis.datasource.pooled.PooledDataSourceFactory;
import com.zhi.mybatis.datasource.unpooled.UnpooledDataSourceFactory;
import com.zhi.mybatis.executor.Executor;
import com.zhi.mybatis.executor.SimpleExecutor;
import com.zhi.mybatis.executor.parameter.ParameterHandler;
import com.zhi.mybatis.executor.resultset.DefaultResultHandler;
import com.zhi.mybatis.executor.resultset.ResultSetHandler;
import com.zhi.mybatis.executor.statement.PrepareStatementHandler;
import com.zhi.mybatis.executor.statement.StatementHandler;
import com.zhi.mybatis.mapping.BoundSql;
import com.zhi.mybatis.mapping.Environment;
import com.zhi.mybatis.mapping.MappedStatement;
import com.zhi.mybatis.reflection.MetaObject;
import com.zhi.mybatis.reflection.factory.DefaultObjectFactory;
import com.zhi.mybatis.reflection.factory.ObjectFactory;
import com.zhi.mybatis.reflection.wrapper.DefaultObjectWrapperFactory;
import com.zhi.mybatis.reflection.wrapper.ObjectWrapperFactory;
import com.zhi.mybatis.scripting.LanguageDriver;
import com.zhi.mybatis.scripting.LanguageDriverRegister;
import com.zhi.mybatis.scripting.xmltags.XMLLanguageDriver;
import com.zhi.mybatis.transaction.Transaction;
import com.zhi.mybatis.transaction.jdbc.JdbcTransactionFactory;
import com.zhi.mybatis.type.TypeAliasRegistry;
import com.zhi.mybatis.type.TypeHandlerRegistry;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
    /**
     * 初始化类型别名注册机
     */
    protected final TypeAliasRegistry typeAliasRegistry = new TypeAliasRegistry();

    protected final TypeHandlerRegistry typeHandlerRegistry = new TypeHandlerRegistry();

    protected final LanguageDriverRegister languageDriverRegister = new LanguageDriverRegister();

    // 对象工厂和对象包装器工厂
    protected ObjectFactory objectFactory = new DefaultObjectFactory();

    protected ObjectWrapperFactory objectWrapperFactory = new DefaultObjectWrapperFactory();

    protected final Set<String> loadResources = new HashSet<>();

    protected String databaseId;

    /**
     * 初始化configuration配置类的默认状态
     * 目前初始化池化的类型别名 JDBC、DRUID POOLED UNPOOLED
     */
    public Configuration() {
        // 配置连接池
        typeAliasRegistry.registerAlias("JDBC", JdbcTransactionFactory.class);
        typeAliasRegistry.registerAlias("DRUID", DruidDataSourceFactory.class);
        // 有池化
        typeAliasRegistry.registerAlias("POOLED", PooledDataSourceFactory.class);
        // 无池化
        typeAliasRegistry.registerAlias("UNPOOLED", UnpooledDataSourceFactory.class);

        languageDriverRegister.setDefaultDriverClass(XMLLanguageDriver.class);
    }
    public void addMappers(String packageName) {
        mapperRegister.addMappers(packageName);
    }

    //添加Mapper
    // IUserDao
    public <T> void addMapper(Class<?> type) {
        mapperRegister.addMapper(type);
    }

    public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
        return mapperRegister.getMapper(type, sqlSession);
    }

    public boolean hasMapper(Class<?> type) {
        return mapperRegister.hashMap(type);
    }

    /**
     * 添加解析sql -> statementMap
     * @param ms
     */
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

    public String getDatabaseId() {
        return databaseId;
    }

    public ResultSetHandler newResultHandler(Executor executor, MappedStatement mappedStatement, BoundSql boundSql) {
        return new DefaultResultHandler(executor, mappedStatement, boundSql);
    }
    public StatementHandler newStatementHandler(Executor executor, MappedStatement mappedStatement, Object parameter, ResultHandler resultHandler, BoundSql boundSql) {
        return new PrepareStatementHandler(executor, mappedStatement, parameter, resultHandler, boundSql);
    }

    public Executor newExecutor(Transaction tx) {
        return new SimpleExecutor(this, tx);
    }

    // 创建元对象
    public MetaObject newMetaObject(Object object) {
        return MetaObject.forObject(object, objectFactory, objectWrapperFactory);
    }

    public boolean isResourceLoaded(String resource) {
        return loadResources.contains(resource);
    }

    public void addLoadedResource(String resource) {
        loadResources.add(resource);
    }

    public LanguageDriverRegister getLanguageRegistry() {
        return languageDriverRegister;
    }

    public TypeHandlerRegistry getTypeHandlerRegistry() {
        return typeHandlerRegistry;
    }

    public ParameterHandler newParameterHandler(MappedStatement mappedStatement, Object parameter, BoundSql boundSql) {
        // 创建参数处理器
        ParameterHandler parameterHandler = mappedStatement.getLang().createParameterHandler(mappedStatement, parameter, boundSql);
        // 插件的一些参数，也是在这里处理，暂时不添加这部分内容 interceptorChain.pluginAll(parameterHandler);
        return parameterHandler;
    }

    public LanguageDriver getDefaultScriptingLanguageInstance() {
        return languageDriverRegister.getDefaultDriver();
    }
}
