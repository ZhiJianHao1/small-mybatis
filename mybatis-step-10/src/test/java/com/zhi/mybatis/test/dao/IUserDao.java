package com.zhi.mybatis.test.dao;

import com.zhi.mybatis.test.po.User;

import java.util.List;

/**
 * @author zhijianhao
 * @created by zhijianhao on 2023/3/23-23:07
 */
public interface IUserDao {
    User queryUserInfoById(Long id);

    User queryUserInfo(User req);

    List<User> queryUserInfoList();

    int updateUserInfo(User req);

    void insertUserInfo(User req);

    int deleteUserInfoByUserId(String userId);
}
