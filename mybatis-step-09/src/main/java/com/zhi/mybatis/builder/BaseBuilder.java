package com.zhi.mybatis.builder;

import com.zhi.mybatis.session.Configuration;
import com.zhi.mybatis.type.TypeAliasRegistry;
import com.zhi.mybatis.type.TypeHandlerRegistry;

/**
 * 构建起基类 建造者模式
 * @author zhijianhao
 * @created by zhijianhao on 2023/3/23-23:23
 */
public class BaseBuilder {
    protected final Configuration configuration;

    protected final TypeAliasRegistry typeAliasRegistry;

    protected final TypeHandlerRegistry typeHandlerRegistry;

    /**
     * 利用构造初始化对象
     * 创建对象时初始化对象状态
     * 无参构造初始化对象的默认状态
     * 有参构造允许创建对象时指定初始状态
     * @param configuration 配置 负责mybatis整体的配置 数据库连接、映射器、类型处理器、映射器等
     */
    /**
     * configuration 的配置
     * 数据源和事务管理器配置（配置数据库连接池、事务管理器）是应用程序连接数据库的关键组件
     * 映射器（mapper）的配置（将Java对象映射到数据库表格中的数据，或者将数据库的数据映射到Java对象中）
     * 类型处理器的配置（主要用于将Java类型和数据库类型之间进行转换）
     * 全局属性配置 日志、缓存
     *
     * @param configuration
     */
    public BaseBuilder(Configuration configuration) {
        this.configuration = configuration;
        this.typeAliasRegistry = configuration.getTypeAliasRegistry();
        this.typeHandlerRegistry = configuration.getTypeHandlerRegistry();
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    protected Class<?> resolveAlias(String alias) {
        return typeAliasRegistry.resolveAlias(alias);
    }
}
