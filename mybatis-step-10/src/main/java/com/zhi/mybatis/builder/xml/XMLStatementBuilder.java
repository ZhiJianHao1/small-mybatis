package com.zhi.mybatis.builder.xml;

import com.zhi.mybatis.builder.BaseBuilder;
import com.zhi.mybatis.builder.MapperBuilderAssistant;
import com.zhi.mybatis.mapping.SqlCommandType;
import com.zhi.mybatis.mapping.SqlSource;
import com.zhi.mybatis.scripting.LanguageDriver;
import com.zhi.mybatis.session.Configuration;
import org.dom4j.Element;

import java.util.Locale;

/**
 * XML语句构建器
 * @author zhijianhao
 */
public class XMLStatementBuilder extends BaseBuilder {

    private Element element;

    private MapperBuilderAssistant builderAssistant;

    public XMLStatementBuilder(Configuration configuration, Element element, MapperBuilderAssistant builderAssistant) {
        super(configuration);
        this.element = element;
        this.builderAssistant = builderAssistant;
    }

    //解析语句(select|insert|update|delete)
    //<select
    //  id="selectPerson"
    //  parameterType="int"
    //  parameterMap="deprecated"
    //  resultType="hashmap"
    //  resultMap="personResultMap"
    //  flushCache="false"
    //  useCache="true"
    //  timeout="10000"
    //  fetchSize="256"
    //  statementType="PREPARED"
    //  resultSetType="FORWARD_ONLY">
    //  SELECT * FROM PERSON WHERE ID = #{id}
    //</select>
    public void parseStatementNode() {
        String id = element.attributeValue("id");
        // 参数类型
        String parameterType = element.attributeValue("parameterType");
        Class<?> parameterTypeClass = resolveAlias(parameterType);
        // 外部应用 resultMap
        String resultMap = element.attributeValue("resultMap");
        // 结果类型
        String resultType = element.attributeValue("resultType");
        Class<?> resultTypeClass = resolveAlias(resultType);
        // 获取命令类型 (select|insert|update|delete)
        String nodeName = element.getName();
        SqlCommandType sqlCommandType = SqlCommandType.valueOf(nodeName.toUpperCase(Locale.ENGLISH));

        // 获取默认语言驱动器
        Class<?> langClass = configuration.getLanguageRegistry().getDefaultDriverClass();
        LanguageDriver langDriver = configuration.getLanguageRegistry().getDriver(langClass);
        // 解析成SqlSource，DynamicSqlSource/RawSqlSource
        SqlSource sqlSource = langDriver.createSqlSource(configuration, element, parameterTypeClass);

        // 调用助手类
        builderAssistant.addMappedStatement(id,
                sqlSource,
                sqlCommandType,
                parameterTypeClass,
                resultMap,
                resultTypeClass,
                langDriver);
    }
}
