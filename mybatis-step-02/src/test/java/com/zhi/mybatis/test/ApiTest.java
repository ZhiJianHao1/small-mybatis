package com.zhi.mybatis.test;

import com.zhi.binding.MapperRegister;
import com.zhi.mybatis.test.dao.IUserDao;
import com.zhi.session.SqlSession;
import com.zhi.session.SqlSessionFactory;
import com.zhi.session.defaults.DefaultSqlSessionFactory;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhijianhao
 * @created by zhijianhao on 2023/3/22-22:36
 */
public class ApiTest {

    private Logger logger = LoggerFactory.getLogger(ApiTest.class);

    @Test
    public void test_mapperRegister() {
        MapperRegister mapperRegister = new MapperRegister();
        mapperRegister.addMappers("com.zhi.mybatis.test.dao");

        SqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(mapperRegister);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
        String result = userDao.queryUserName("10001");

        logger.info("result {}", result);
    }
}
