package com.zhi.mybatis.test.dao;

import com.zhi.mybatis.annotations.Delete;
import com.zhi.mybatis.annotations.Insert;
import com.zhi.mybatis.annotations.Select;
import com.zhi.mybatis.annotations.Update;
import com.zhi.mybatis.test.po.User;

import java.util.List;

/**
 * @author zhijianhao
 * @created by zhijianhao on 2023/3/23-23:07
 */
public interface IUserDao {
    @Select("SELECT id, userId, userName, userHead\n" +
            "FROM user\n" +
            "where id = #{id}")
    User queryUserInfoById(Long id);

    @Select("SELECT id, userId, userName, userHead\n" +
            "        FROM user\n" +
            "        where id = #{id}")
    User queryUserInfo(User req);

    @Select("SELECT id, userId, userName, userHead\n" +
            "FROM user")
    List<User> queryUserInfoList();

    @Update("UPDATE user\n" +
            "SET userName = #{userName}\n" +
            "WHERE id = #{id}")
    int updateUserInfo(User req);

    @Insert("INSERT INTO user\n" +
            "(userId, userName, userHead, createTime, updateTime)\n" +
            "VALUES (#{userId}, #{userName}, #{userHead}, now(), now())")
    void insertUserInfo(User req);

    @Delete("DELETE FROM user WHERE userId = #{userId}")
    int deleteUserInfoByUserId(String userId);
}
