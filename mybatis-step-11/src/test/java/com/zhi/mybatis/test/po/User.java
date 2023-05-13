package com.zhi.mybatis.test.po;

import java.util.Date;

/**
 * @author zhijianhao
 * @created by zhijianhao on 2023/3/23-23:07
 */
public class User {
    private Long id;
    private String userId;          // 用户ID

    private String userName;        // 用户名称
    private String userHead;        // 头像
    private Date createTime;        // 创建时间
    private Date updateTime;        // 更新时间

    public User() {
    }

    public User(Long id) {
        this.id = id;
    }

    public User(Long id, String userId) {
        this.id = id;
        this.userId = userId;
    }

    public User(Long id, String userId, String userName) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserHead() {
        return userHead;
    }

    public void setUserHead(String userHead) {
        this.userHead = userHead;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
