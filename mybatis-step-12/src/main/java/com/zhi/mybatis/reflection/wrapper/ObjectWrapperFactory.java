package com.zhi.mybatis.reflection.wrapper;

import com.zhi.mybatis.reflection.MetaObject;

/**
 * 对象包装工厂
 * @author zhijianhao
 */
public interface ObjectWrapperFactory {

    /**
     * 判断是否有没有包装器
     * @param object
     * @return
     */
    boolean hasWrapperFor(Object object);

    /**
     * 得到包装器
     * @param metaObject
     * @param object
     * @return
     */
    ObjectWrapper getWrapperFor(MetaObject metaObject, Object object);
}
