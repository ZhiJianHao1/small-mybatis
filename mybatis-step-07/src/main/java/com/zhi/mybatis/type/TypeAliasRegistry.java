package com.zhi.mybatis.type;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * 类型别名注注册机
 * @author zhijianhao
 * @created by zhijianhao on 2023/3/25-15:39
 */
public class TypeAliasRegistry {

    private final Map<String, Class<?>> TYPE_ALIASES = new HashMap<>();

    public TypeAliasRegistry() {
        // 构建函数里注册系统内置的类型别名
        registerAlias("String", String.class);
        // 基本包装类型
        registerAlias("byte", Byte.class);
        registerAlias("long", Long.class);
        registerAlias("short", Short.class);
        registerAlias("int", Integer.class);
        registerAlias("integer", Integer.class);
        registerAlias("double", Double.class);
        registerAlias("float", Float.class);
        registerAlias("boolean", Boolean.class);
    }

    /**
     * 将类型别名放入TYPE_ALIASES MAP中缓存起来
     * @param alias
     * @param value
     */
    public void registerAlias(String alias, Class<?> value) {

        String key = alias.toLowerCase(Locale.ENGLISH);
        TYPE_ALIASES.put(key, value);
    }

    public <T> Class<T> resolveAlias(String string) {
        String key = string.toLowerCase(Locale.ENGLISH);
        return (Class<T>) TYPE_ALIASES.get(key);
    }
}
