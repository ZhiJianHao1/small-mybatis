package com.zhi.mybatis.executor.result;

import com.zhi.mybatis.session.ResultContext;

/**
 * 默认结果上下文
 * @author zhijianhao
 * @created by zhijianhao on 2023/5/3-17:19
 */
public class DefaultResultContext implements ResultContext {

    private Object resultObject;

    private int resultCount;

    public DefaultResultContext() {
        this.resultObject = null;
        this.resultCount = 0;
    }

    @Override
    public Object getResultObject() {
        return resultObject;
    }

    @Override
    public int getResultCount() {
        return resultCount;
    }

    public void nextResultObject(Object resultObject) {
        resultCount++;
        this.resultObject = resultObject;
    }
}
