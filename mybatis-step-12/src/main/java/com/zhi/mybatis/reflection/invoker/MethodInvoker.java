package com.zhi.mybatis.reflection.invoker;

import java.lang.reflect.Method;

/**
 * 方法调用者
 * @author zhijianhao
 */
public class MethodInvoker implements Invoker {

    private Method method;
    private Class<?> type;

    public MethodInvoker(Method method) {
        this.method = method;
        // 如果只有一个参数， 返回参数类型，否则返回 return 类型
        if (method.getParameterTypes().length == 1) {
            this.type = method.getParameterTypes()[0];
        } else {
            this.type = method.getReturnType();
        }
    }

    @Override
    public Object invoke(Object target, Object[] args) throws Exception {
        return method.invoke(target, args);
    }

    @Override
    public Class<?> getType() {
        return type;
    }
}
