package com.zhi.mybatis.scripting.xmltags;

import com.zhi.mybatis.executor.parameter.ParameterHandler;
import com.zhi.mybatis.mapping.BoundSql;
import com.zhi.mybatis.mapping.MappedStatement;
import com.zhi.mybatis.mapping.SqlSource;
import com.zhi.mybatis.scripting.LanguageDriver;
import com.zhi.mybatis.scripting.defaults.DefaultParameterHandler;
import com.zhi.mybatis.session.Configuration;
import org.dom4j.Element;

/**
 * XML语言驱动器
 * @author zhijianhao
 */
public class XMLLanguageDriver implements LanguageDriver {
    @Override
    public SqlSource createSqlSource(Configuration configuration, Element script, Class<?> parameterType) {
        XMLScriptBuilder xmlScriptBuilder = new XMLScriptBuilder(configuration, script, parameterType);
        return xmlScriptBuilder.parseScriptNode();
    }

    @Override
    public ParameterHandler createParameterHandler(MappedStatement mappedStatement, Object parameter, BoundSql boundSql) {
        return new DefaultParameterHandler(mappedStatement, parameter, boundSql);
    }


}
