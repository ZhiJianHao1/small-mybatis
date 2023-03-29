package com.zhi.mybatis.session.defaults;


import com.zhi.mybatis.mapping.BoundSql;
import com.zhi.mybatis.mapping.Environment;
import com.zhi.mybatis.mapping.MappedStatement;
import com.zhi.mybatis.session.Configuration;
import com.zhi.mybatis.session.SqlSession;

import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zhijianhao
 * @created by zhijianhao on 2023/3/22-22:28
 */
public class DefaultSqlSession implements SqlSession {

    private Configuration configuration;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <T> T selectOne(String statement) {
        return (T) ("你的操作被代理了！" + statement);
    }

    @Override
    public <T> T selectOne(String statement, Object parameter) {
        try {
            MappedStatement mappedStatement = configuration.getMappedStatement(statement);
            // 配置项
            Environment environment = configuration.getEnvironment();

            Connection connection = environment.getDataSource().getConnection();
            // 绑定的sql
            BoundSql boundSql = mappedStatement.getBoundSql();
            /**
             * prepareStatement() 方法是 JDBC 中 Connection 接口的一个方法，用于创建一个预编译的 SQL 语句对象。
             * 与 createStatement() 方法不同，prepareStatement() 方法可以通过占位符（?）来设置参数，以避免 SQL 注入攻击，提高代码的安全性和可读性。
             */
            PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSql());
            // 设置参数值
            preparedStatement.setLong(1, Long.parseLong(((Object[]) parameter)[0].toString()));
            // 执行查询
            ResultSet resultSet = preparedStatement.executeQuery();

            List<T> objList = resultSet2Obj(resultSet, Class.forName(boundSql.getResultType()));
            return objList.get(0);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private <T> List<T> resultSet2Obj(ResultSet resultSet, Class<?> clazz) {
        List<T> list = new ArrayList<>();
        try {
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            // 每次遍历行值
            while (resultSet.next()) {
                T obj = (T) clazz.newInstance();
                for (int i = 1; i <= columnCount; i++) {
                    Object value = resultSet.getObject(i);
                    String columnName = metaData.getColumnName(i);
                    String setMethod = "set" + columnName.substring(0, 1).toUpperCase() + columnName.substring(1);
                    Method method;
                    if (value instanceof Timestamp) {
                        method = clazz.getMethod(setMethod, Date.class);
                    } else {
                        method = clazz.getMethod(setMethod, value.getClass());
                    }
                    method.invoke(obj, value);
                }
                list.add(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Configuration getConfiguration() {
        return configuration;
    }

    @Override
    public <T> T getMapper(Class<T> type) {
        return configuration.getMapper(type, this);
    }
}
