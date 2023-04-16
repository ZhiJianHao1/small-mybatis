package com.zhi.mybatis.test;

import com.alibaba.fastjson.JSON;
import com.zhi.mybatis.io.Resources;
import com.zhi.mybatis.session.SqlSession;
import com.zhi.mybatis.session.SqlSessionFactory;
import com.zhi.mybatis.session.SqlSessionFactoryBuilder;
import com.zhi.mybatis.test.dao.IUserDao;
import com.zhi.mybatis.test.po.User;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ApiTest {
    private final Logger logger = LoggerFactory.getLogger(ApiTest.class);

    private SqlSession sqlSession;

    @Before
    public void init() throws IOException {
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourcesAsReader("mybatis-config-datasource.xml"));
        sqlSession = sqlSessionFactory.openSession();
    }

    @Test
    public void test_queryUserByInfo() {
        // 1. 获取映射器对象
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);

        // 2. 测试验证：基本参数
        User user = userDao.queryUserInfoById(1L);
        logger.info("测试结果：{}", JSON.toJSONString(user));
    }

    @Test
    public void test_queryUserInfo() {
        // 1. 获取映射器对象
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);

        // 2. 测试验证：对象参数
        User user = userDao.queryUserInfo(new User(1L, "10001"));
        logger.info("测试结果：{}", JSON.toJSONString(user));
    }
}
