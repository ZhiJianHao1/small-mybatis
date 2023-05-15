package com.zhi.mybatis.test;

import com.alibaba.fastjson.JSON;
import com.zhi.mybatis.io.Resources;
import com.zhi.mybatis.session.SqlSession;
import com.zhi.mybatis.session.SqlSessionFactory;
import com.zhi.mybatis.session.SqlSessionFactoryBuilder;
import com.zhi.mybatis.test.dao.IActivityDao;
import com.zhi.mybatis.test.po.Activity;
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
        // 1.从SqlSessionFactory中获取SqlSession
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourcesAsReader("mybatis-config-datasource.xml"));
        sqlSession = sqlSessionFactory.openSession();
    }

    @Test
    public void test_queryActivityById() {
        // 1. 获取映射器对象
        IActivityDao dao = sqlSession.getMapper(IActivityDao.class);
        // 2. 测试验证
        Activity res = dao.queryActivityById(100001L);
        logger.info("测试结果：{}", JSON.toJSONString(res));
    }

}
