package com.zhi.mybatis.test.dao;

import com.zhi.mybatis.test.po.User;

/**
 * @author zhijianhao
 * @created by zhijianhao on 2023/3/23-23:07
 */
public interface IUserDao {
    User queryUserInfoById(Long uId);
}
