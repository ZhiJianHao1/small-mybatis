package com.zhi.mybatis.builder.xml;

import com.zhi.mybatis.builder.BaseBuilder;
import com.zhi.mybatis.datasource.DataSourceFactory;
import com.zhi.mybatis.io.Resources;
import com.zhi.mybatis.mapping.Environment;
import com.zhi.mybatis.session.Configuration;
import com.zhi.mybatis.transaction.TransactionFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.InputSource;

import javax.sql.DataSource;
import java.io.InputStream;
import java.io.Reader;
import java.util.List;
import java.util.Properties;

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

    /*
     * <mappers>
     *	 <mapper resource="org/mybatis/builder/AuthorMapper.xml"/>
     *	 <mapper resource="org/mybatis/builder/BlogMapper.xml"/>
     *	 <mapper resource="org/mybatis/builder/PostMapper.xml"/>
     * </mappers>
     */
    private void mapperElement(Element mappers) throws Exception {
        List<Element> mapperList = mappers.elements("mapper");
        for (Element element : mapperList) {
            String resource = element.attributeValue("resource");
            String mapperClass = element.attributeValue("class");
            // XML 解析饿
            if (resource != null && mapperClass == null) {
                InputStream inputStream = Resources.getResourcesAsStream(resource);
                // 在for循环里每个mapper都重新new一个XMLMapperBuilder，来接写
                XMLMapperBuilder mapperParser = new XMLMapperBuilder(inputStream, configuration, resource);
                mapperParser.parse();
            }
            // Annotation 注解解析
            else if (resource == null && mapperClass != null) {
                Class<?> mapperInterface = Resources.classForName(mapperClass);
                configuration.addMapper(mapperInterface);
            }
        }
    }
}
