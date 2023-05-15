package com.zhi.mybatis.test.dao;

import com.zhi.mybatis.test.po.Activity;

/**
 * TODO
 *
 * @author：zhijianhao
 * @date: 2023/5/14
 */
public interface IActivityDao {
    Activity queryActivityById(Long activityId);
}
