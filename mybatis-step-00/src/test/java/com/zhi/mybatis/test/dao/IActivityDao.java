package com.zhi.mybatis.test.dao;

import com.zhi.mybatis.test.po.Activity;

public interface IActivityDao {

    Activity queryActivityById(Activity activity);

    Integer insert(Activity activity);

}
