package com.zhi.mybatis.test.dao;

/**
 * @author zhijianhao
 * @created by zhijianhao on 2023/3/21-23:02
 */
public interface IUserDao {
    /**
     * 查询用户名称
     * @param uId 用户id
     * @return String
     */
    String queryUserName(String uId);

    /**
     * 查询用户年来
     * @param uId 用户id
     * @return Integer
     */
    Integer queryUserAge(String uId);
}
