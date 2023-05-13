package com.zhi.mybatis.executor.result;

import com.zhi.mybatis.reflection.factory.ObjectFactory;
import com.zhi.mybatis.session.ResultContext;
import com.zhi.mybatis.session.ResultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * 默认结果处理器
 * @author zhijianhao
 * @created by zhijianhao on 2023/5/3-17:03
 */
public class DefaultResultHandler implements ResultHandler {
    private final List<Object> list;

    public DefaultResultHandler() {
        this.list = new ArrayList<>();
    }

    @SuppressWarnings("unchecked")
    public DefaultResultHandler(ObjectFactory objectFactory) {
        this.list = objectFactory.create(List.class);
    }

    @Override
    public void handlerResult(ResultContext context) {
        list.add(context.getResultObject());
    }

    public List<Object> getResultList() {
        return list;
    }
}
