package com.zhi.mybatis.builder;

import com.zhi.mybatis.session.Configuration;

/**
 * 构建起基类 建造者模式
 * @author zhijianhao
 * @created by zhijianhao on 2023/3/23-23:23
 */
public class BaseBuilder {
    protected final Configuration configuration;

    public BaseBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    public Configuration getConfiguration() {
        return configuration;
    }
}
