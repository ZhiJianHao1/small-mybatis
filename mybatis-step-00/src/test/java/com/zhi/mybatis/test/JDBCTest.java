package com.zhi.mybatis.test;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author zhijianhao
 * @created by zhijianhao on 2023/3/21-22:18
 */
public class JDBCTest {

    @Test
    public void test_jdbc() throws Exception {
        // 1.加载驱动
        Class.forName("com.mysql.jdbc.Driver");

        // 2.连接信息
        String url = "jdbc:mysql://127.0.0.1:3306/mybatis?useUnicode=true&characterEncoding=utf8&useSSL=false";
        String username = "root";
        String password = "123456";

        // 3.连接成功
        Connection connection = DriverManager.getConnection(url, username, password);

        // 4.执行SQL的对象获取
        Statement statement = connection.createStatement();

        // 5.待执行SQL
        String sql = "SELECT id, userId, userName, userHead FROM user";

        // 6.执行SQL，并输出结果
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            System.out.print("id=" + resultSet.getObject("id") + " ");
            System.out.print("userId=" + resultSet.getObject("userId") + " ");
            System.out.print("userName=" + resultSet.getObject("userName") + " ");
            System.out.print("userHead=" + resultSet.getObject("userHead"));
        }

        // 7.释放连接
        resultSet.close();
        statement.close();
        connection.close();
    }

}
