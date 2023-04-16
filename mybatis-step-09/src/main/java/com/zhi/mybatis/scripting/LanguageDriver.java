package com.zhi.mybatis.scripting;

import com.zhi.mybatis.executor.parameter.ParameterHandler;
import com.zhi.mybatis.mapping.BoundSql;
import com.zhi.mybatis.mapping.MappedStatement;
import com.zhi.mybatis.mapping.SqlSource;
import com.zhi.mybatis.session.Configuration;
import org.dom4j.Element;

/**
 * 脚本语言驱动
 * @author zhijianhao
 */
public interface LanguageDriver {

    /**
     * 创建 SQL 源码 (mapper xml方式)
     * @param configuration
     * @param script
     * @param parameterType
     * @return
     */
    SqlSource createSqlSource(Configuration configuration, Element script, Class<?> parameterType);

    /**
     * 创建参数处理器
     * @param mappedStatement
     * @param parameter
     * @param boundSql
     * @return
     */
    ParameterHandler createParameterHandler(MappedStatement mappedStatement, Object parameter, BoundSql boundSql);
}
