package com.zhi.mybatis.scripting;

import com.zhi.mybatis.mapping.SqlSource;
import com.zhi.mybatis.session.Configuration;
import org.dom4j.Element;

/**
 * 脚本语言驱动
 * @author zhijianhao
 */
public interface LanguageDriver {

    SqlSource createSqlSource(Configuration configuration, Element script, Class<?> parameterType);
}
