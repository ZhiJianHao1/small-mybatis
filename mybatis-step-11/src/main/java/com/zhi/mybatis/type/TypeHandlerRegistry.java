package com.zhi.mybatis.type;

import java.lang.reflect.Type;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

/**
 * 类型处理器注册机
 * @author zhijianhao
 */
public final class TypeHandlerRegistry {

    private final Map<JdbcType, TypeHandler<?>> JDBC_TYPE_HANDLER_MAP = new EnumMap<>(JdbcType.class);

    private final Map<Type, Map<JdbcType, TypeHandler<?>>> TYPE_HANDLER_MAP = new HashMap<>();

    private final Map<Class<?>, TypeHandler<?>> ALL_TYPE_HANDLERS_MAP = new HashMap<>();


    public TypeHandlerRegistry() {
        registry(Long.class, new LongTypeHandler());
        registry(long.class, new LongTypeHandler());

        registry(String.class, new StringTypeHandler());

        registry(String.class, JdbcType.CHAR, new StringTypeHandler());
        registry(String.class, JdbcType.VARCHAR, new StringTypeHandler());
    }

    private <T> void registry(Type javaType, TypeHandler<? extends T> typeHandler) {
        registry(javaType, null, typeHandler);
    }

    private void registry(Type javaType, JdbcType jdbcType, TypeHandler<?> handler) {
        if (null != javaType) {
            Map<JdbcType, TypeHandler<?>> map = TYPE_HANDLER_MAP.computeIfAbsent(javaType, k -> new HashMap<>());
            map.put(jdbcType, handler);
        }
        ALL_TYPE_HANDLERS_MAP.put(handler.getClass(), handler);
    }

    @SuppressWarnings("unchecked")
    public <T> TypeHandler<T> getTypeHandler(Class<T> type, JdbcType jdbcType) {
        return getTypeHandler((Type) type, jdbcType);
    }

    public boolean hasTypeHandler(Class<?> jdbcType) {
        return hasTypeHandler(jdbcType, null);
    }

    public boolean hasTypeHandler(Class<?> javaType, JdbcType jdbcType) {
        return javaType != null && getTypeHandler((Type) javaType, jdbcType) != null;
    }

    private <T> TypeHandler<T> getTypeHandler(Type type, JdbcType jdbcType) {
        Map<JdbcType, TypeHandler<?>> jdbcTypeHandlerMap = TYPE_HANDLER_MAP.get(type);
        TypeHandler<?> handler = null;
        if (jdbcTypeHandlerMap != null) {
            handler = jdbcTypeHandlerMap.get(jdbcType);
            if (handler == null) {
                handler = jdbcTypeHandlerMap.get(null);
            }
        }
        return (TypeHandler<T>) handler;
    }
}
