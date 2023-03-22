package com.zhi.mybatis.test.dao;

/**
 * @author zhijianhao
 * @created by zhijianhao on 2023/3/22-22:37
 */
public interface IUserDao {
    String queryUserName(String uId);

    Integer queryUserAge(String uId);
}
