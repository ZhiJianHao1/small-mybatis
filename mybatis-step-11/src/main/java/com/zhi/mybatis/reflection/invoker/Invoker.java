package com.zhi.mybatis.reflection.invoker;

/**
 * 反射调用者
 * @author zhijianhao
 */
public interface Invoker {

    public Object invoke(Object target, Object[] args) throws Exception;

    public Class<?> getType();
}
