package com.zhi.mybatis.test;

import com.alibaba.fastjson.JSON;
import com.zhi.mybatis.binding.MapperProxyFactory;
import com.zhi.mybatis.test.dao.IUserDao;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhijianhao
 * @created by zhijianhao on 2023/3/21-23:02
 */
public class ApiTest {

    private Logger logger = LoggerFactory.getLogger(ApiTest.class);

    @Test
    public void test_MapperProxyFactory() {
        MapperProxyFactory<IUserDao> factory = new MapperProxyFactory<>(IUserDao.class);
        Map<String, String> sqlSession = new HashMap<>();

        sqlSession.put("com.zhi.mybatis.test.dao.IUserDao.queryUserName", "模拟执行 Mapper.xml 中 SQL 语句的操作：查询用户姓名");
        sqlSession.put("com.zhi.mybatis.test.dao.IUserDao.queryUserAge", "模拟执行 Mapper.xml 中 SQL 语句的操作：查询用户年龄");

        IUserDao userDao = factory.newInterface(sqlSession);
        String result = userDao.queryUserName("1001");
        logger.info("result: {}", result);
    }

    @Test
    public void test_mapperInterface() {
        IUserDao userDao = (IUserDao) Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader(),
                new Class[]{IUserDao.class},
                ((proxy, method, args) -> "你被代理了！"));
        String result = userDao.queryUserName("1");
        logger.info("result: {}", JSON.toJSONString(result));
    }
}
