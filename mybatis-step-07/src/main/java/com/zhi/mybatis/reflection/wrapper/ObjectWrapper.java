package com.zhi.mybatis.reflection.wrapper;

import com.zhi.mybatis.reflection.MetaObject;
import com.zhi.mybatis.reflection.factory.ObjectFactory;
import com.zhi.mybatis.reflection.property.PropertyTokenizer;

import java.util.List;

/**
 * 对象包装器
 * @author zhijianhao
 */
public interface ObjectWrapper {
    // get
    Object get(PropertyTokenizer prop);

    // set
    void set(PropertyTokenizer prop, Object value);

    // 查找属性
    String findProperty(String name, boolean useCamelCaseMapping);

    // 取得getter的名字列表
    String[] getGetterNames();

    // 取得setter的名字列表
    String[] getSetterNames();

    // 取得setter的类型
    Class<?> getSetterType(String name);

    // 取得getter的类型
    Class<?> getGetterType(String name);

    // 是否setter
    boolean hasSetter(String name);

    // 是否指定getter
    boolean hasGetter(String name);

    // 实力化属性
    MetaObject instantiatePropertyValue(String name, PropertyTokenizer prop, ObjectFactory objectFactory);

    // 是否集合
    boolean isCollection();

    // 添加属性
    void add(Object element);

    // 添加属性
    <E> void addAll(List<E> element);
}
