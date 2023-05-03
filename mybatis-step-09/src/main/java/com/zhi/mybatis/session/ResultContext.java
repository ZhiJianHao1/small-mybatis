package com.zhi.mybatis.session;

/**
 * 结果上下文
 * @author zhijianhao
 * @created by zhijianhao on 2023/5/3-17:06
 */
public interface ResultContext {
    /**
     * 获取结果
     * @return
     */
    Object getResultObject();

    /**
     * 获取记录数
     * @return
     */
    int getResultCount();
}
