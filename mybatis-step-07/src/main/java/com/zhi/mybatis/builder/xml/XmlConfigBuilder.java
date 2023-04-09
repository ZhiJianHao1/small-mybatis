package com.zhi.mybatis.builder.xml;

import com.zhi.mybatis.builder.BaseBuilder;
import com.zhi.mybatis.datasource.DataSourceFactory;
import com.zhi.mybatis.io.Resources;
import com.zhi.mybatis.mapping.BoundSql;
import com.zhi.mybatis.mapping.Environment;
import com.zhi.mybatis.mapping.MappedStatement;
import com.zhi.mybatis.mapping.SqlCommandType;
import com.zhi.mybatis.session.Configuration;
import com.zhi.mybatis.transaction.TransactionFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.InputSource;

import javax.sql.DataSource;
import java.io.Reader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * XML配置构建器，建造者模式，继承BaseBuilder
 * @author zhijianhao
 * @created by zhijianhao on 2023/3/23-23:23
 */
// XmlConfigBuilder 继承父类 BaseBuilder
public class XmlConfigBuilder extends BaseBuilder {

    private Element root;

    public XmlConfigBuilder(Reader reader) {
        // 调用父类初始化 Configuration 配置类
        super(new Configuration());
        SAXReader saxReader = new SAXReader();
        try {
            /**
             * 使用SAX reader方法解析起解析XML文档 成一个Document对象
             */
            Document document = saxReader.read(new InputSource(reader));
            root = document.getRootElement();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析配置：类型别名、插件、工厂、对象包装、设置、环境、类型转换、映射器
     * @return
     */
    public Configuration parse() {
        try {
            // 环境配置
            // <environments default="development"> 读取父节点environments
            environmentsElement(root.element("environments"));
            // 解析映射器
            // 读取父级节点 <mappers>
            mapperElement(root.element("mappers"));
        } catch (Exception e) {
            throw new RuntimeException("Error parsing SQL Mapper Configuration. Cause: " + e, e);
        }
        // 解析mybatis-config-datasource.xml 环境完成
        // 解析 User_Mapper.xml 映射器完成 并将映射器代理工厂添加到map中
        // 配置类加载完成
        return configuration;
    }

    /**
     * 环境配置  -> 解析mybatis-config-datasource.xml 下面的environments标签内容
     * 配置JDBC连接 连接池
     * @param context
     */
    /**
     *     <environments default="development">
     *         <environment id="development">
     *             <transactionManager type="JDBC"/>
     *             <dataSource type="POOLED">
     *                 <property name="driver" value="com.mysql.jdbc.Driver"/>
     *                 <property name="url" value="jdbc:mysql://127.0.0.1:3306/mybatis?useUnicode=true"/>
     *                 <property name="username" value="root"/>
     *                 <property name="password" value="123456"/>
     *             </dataSource>
     *         </environment>
     *     </environments>
     *
     *     <mappers>
     *         <mapper resource="mapper/User_Mapper.xml"/>
     *     </mappers>
     * @param context
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    // 环境配置
    private void environmentsElement(Element context) throws InstantiationException, IllegalAccessException {
        // 获取父级标签 default 属性的值
        String environment = context.attributeValue("default");
        // 读取标签 environment
        List<Element> environmentList = context.elements("environment");
        for (Element e : environmentList) {
            String id = e.attributeValue("id");
            if (environment.equals(id)) {
                // 事务管理器
                // 创建指定类的实例对象  事务
                // <transactionManager type="JDBC"/>
                // 读取 transactionManager type属性的值，并创建事务工厂
                // 通过初始化好的 Configuration 配置类 里面的类型别名注册机获取JDBC的事务
                TransactionFactory txFactory = (TransactionFactory) typeAliasRegistry.resolveAlias(e.element("transactionManager").attributeValue("type")).newInstance();

                // 数据源
                // <dataSource type="POOLED">
                Element dataSourceElement = e.element("dataSource");
                // 通过初始化好的 Configuration 配置类 里面的类型别名注册机获取连接池工厂
                DataSourceFactory dataSourceFactory = (DataSourceFactory) typeAliasRegistry.resolveAlias(dataSourceElement.attributeValue("type")).newInstance();
                Properties props = new Properties();
                // <property name="driver" value="com.mysql.jdbc.Driver"/>
                // <property name="url" value="jdbc:mysql://127.0.0.1:3306/mybatis?useUnicode=true"/>
                // <property name="username" value="root"/>
                // <property name="password" value="123456"/>
                // property标签解析
                List<Element> propertyList = dataSourceElement.elements("property");
                for (Element property : propertyList) {
                    // setProperty 方法加锁了 将数据放入HashTable
                    // Properties 继承了 hashtable
                    props.setProperty(property.attributeValue("name"), property.attributeValue("value"));
                }
                // Properties 给到数据源进行配置
                dataSourceFactory.setProperties(props);
                DataSource dataSource = dataSourceFactory.getDataSource();

                // 构建环境 设置事务、数据源
                Environment.Builder environmentBuilder = new Environment.Builder(id)
                        .transactionFactory(txFactory).dataSource(dataSource);
                // configuration 配置类添加环境配置
                configuration.setEnvironment(environmentBuilder.build());
            }
        }
    }

    /**
     * 解析映射器
     *     <mappers>
     *         <mapper resource="mapper/User_Mapper.xml"/>
     *     </mappers>
     * @param mappers
     * @throws Exception
     */
    private void mapperElement(Element mappers) throws Exception {
        /**
         * <mapper namespace="com.zhi.mybatis.test.dao.IUserDao">
         *
         *     <select id="queryUserInfoById" parameterType="java.lang.Long" resultType="com.zhi.mybatis.test.po.User">
         *         SELECT id, userId, userHead, createTime
         *         FROM user
         *         where id = #{id}
         *     </select>
         *
         * </mapper>
         */
        List<Element> mapperList = mappers.elements("mapper");
        for (Element e : mapperList) {
            String resource = e.attributeValue("resource");
            /**
             * resource <mapper resource="mapper/User_Mapper.xml"/>
             * 注意这里又一次通过 classLoader.getResourceAsStream(resource) 去将User_Mapper.xml转换成输入流
             */
            Reader reader = Resources.getResourcesAsReader(resource);
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(new InputSource(reader));
            Element root = document.getRootElement();
            //命名空间
            String namespace = root.attributeValue("namespace");

            // SELECT
            List<Element> selectNodes = root.elements("select");
            for (Element node : selectNodes) {
                String id = node.attributeValue("id");
                // 入参类型
                String parameterType = node.attributeValue("parameterType");
                // 返回Java对象
                String resultType = node.attributeValue("resultType");
                String sql = node.getText();

                // ? 匹配
                Map<Integer, String> parameter = new HashMap<>();
                Pattern pattern = Pattern.compile("(#\\{(.*?)})");
                Matcher matcher = pattern.matcher(sql);
                for (int i = 1; matcher.find(); i++) {
                    // #{id}
                    String g1 = matcher.group(1);
                    // id
                    String g2 = matcher.group(2);
                    parameter.put(i, g2);
                    // 将 id = #{id} 替换为 id = ?
                    sql = sql.replace(g1, "?");
                }

                String msId = namespace + "." + id;
                String nodeName = node.getName();
                // 通过SqlCommandType枚举去匹配关键字 SELECT INSERT UPDATE DELETE
                SqlCommandType sqlCommandType = SqlCommandType.valueOf(nodeName.toUpperCase(Locale.ENGLISH));
                // 绑定sql
                /**
                 * <select id="queryUserInfoById" parameterType="java.lang.Long" resultType="com.zhi.mybatis.test.po.User">
                 *  sql -> SELECT id, userId, userHead, createTime
                 *         FROM user
                 *         where id = ?
                 *  parameter  -> id
                 *  parameterType -> java.lang.Long
                 *  resultType -> com.zhi.mybatis.test.po.User
                 */
                // 绑定sql parameter 传入参数 parameterType 传入参数类型 resultType 返回类型
                BoundSql boundSql = new BoundSql(sql, parameter, parameterType, resultType);
                // 构建 MappedStatement 映射语句类
                MappedStatement mappedStatement = new MappedStatement.Builder(configuration, msId, sqlCommandType, boundSql).build();
                // 配置类中 添加解析 SQL
                configuration.addMappedStatement(mappedStatement);
            }

            // 注册Mapper映射器
            // namespace="com.zhi.mybatis.test.dao.IUserDao"
            // 用于根据类名（类的全限定名）获取该类的 Class 对象
            // 注册到 MapperRegister mapperRegister 映射注册机
            configuration.addMapper(Resources.classForName(namespace));
        }
    }
}
