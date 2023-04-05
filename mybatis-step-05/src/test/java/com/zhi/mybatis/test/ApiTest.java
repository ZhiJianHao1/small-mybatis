package com.zhi.mybatis.test;


import com.alibaba.fastjson.JSON;
import com.zhi.mybatis.datasource.pooled.PooledDataSource;
import com.zhi.mybatis.io.Resources;
import com.zhi.mybatis.session.SqlSession;
import com.zhi.mybatis.session.SqlSessionFactory;
import com.zhi.mybatis.session.SqlSessionFactoryBuilder;
import com.zhi.mybatis.test.dao.IUserDao;
import com.zhi.mybatis.test.po.User;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author zhijianhao
 * @created by zhijianhao on 2023/3/26-16:33
 */
public class ApiTest {
    private final Logger logger = LoggerFactory.getLogger(ApiTest.class);

    @Test
    public void test_SqlSessionFactory() throws IOException {

        /*
          Resources.getResourcesAsReader("mybatis-config-datasource.xml")
          指定 mybatis-config-datasource.xml 文件
          利用classLoader.getResourceAsStream()方法 获取指定文件的输入流
          使用InputStreamReader进行解析成Reader对象
          (Reader 是一个抽象类，是所有字符输入流的父类。Reader对象用于从字符流中读取数据，并以字符串的形式进行处理)

         */
        // 1. 从SqlSessionFactory中获取SqlSession
        /*
          SqlSessionFactory 在Mybatis中用于创建 SqlSession对象工厂类
          SqlSessionFactory 是一个线程安全的对象，用于创建 SqlSession 实例
          SqlSession 是 Mybatis 执行持久化操作主要入口点。
          SqlSessionFactory 主要作用：
          1.提供线程安全的SqlSession实例：SqlSessionFactory是线程安全的
          因为SqlSessionFactory在创建过程中是一个开销比较大的过程，解析xml、加载configuration配置、构建映射器 mapper 一旦初始化之后就无法改变
          SqlSessionFactory 通过单例模式保证线程安全
         */
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourcesAsReader("mybatis-config-datasource.xml"));
        // 创建 SqlSession 对象，用于与数据库进行交互
        /*
         * openSession() 方法的作用是创建一个新的 SqlSession 对象，用于执行 SQL 语句和管理事务。
         * SqlSession 对象是线程不安全的，每个线程应该拥有自己的 SqlSession 对象，因此在使用完毕后需要手动关闭。
         * 可以通过 SqlSession 的 close() 方法来关闭该对象。
         * sqlSession 每一个线程创建一个自己的sqlSession 保证线程安全
         */
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 2. 获取映射器对象
        // 通过动态代理获取一个映射器
        // 获取指定接口的代理对象，通过代理对象来操作数据库
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);

        // 3. 测试验证
        for (int i = 0; i < 50; i++) {
            User user = userDao.queryUserInfoById(1L);
            logger.info("测试结果：{}", JSON.toJSONString(user));
        }
    }

    @Test
    public void test_pooled() throws SQLException, InterruptedException {
        PooledDataSource pooledDataSource = new PooledDataSource();
        pooledDataSource.setDriver("com.mysql.jdbc.Driver");
        pooledDataSource.setUrl("jdbc:mysql://127.0.0.1:3306/mybatis?useUnicode=true");
        pooledDataSource.setUsername("root");
        pooledDataSource.setPassword("123456");
        // 持续获得链接
        while (true) {
            Connection connection = pooledDataSource.getConnection();
            System.out.println(connection);
            Thread.sleep(1000);
            // 注释掉/不注释掉测试
            connection.close();
        }
    }
}
