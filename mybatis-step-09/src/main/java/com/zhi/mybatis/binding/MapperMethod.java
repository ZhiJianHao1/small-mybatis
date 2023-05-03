package com.zhi.mybatis.binding;

import com.zhi.mybatis.mapping.MappedStatement;
import com.zhi.mybatis.mapping.SqlCommandType;
import com.zhi.mybatis.session.Configuration;
import com.zhi.mybatis.session.SqlSession;

import java.lang.reflect.Method;
import java.util.*;

/**
 * 映射器方法
 * @author zhijianhao
 * @created by zhijianhao on 2023/3/25-16:46
 */
public class MapperMethod {
    private final SqlCommand command;
    
    private final MethodSignature method;

    public MapperMethod(Class<?> mapperInterface, Method method, Configuration configuration) {
        this.command = new SqlCommand(configuration, mapperInterface, method);
        this.method = new MethodSignature(configuration, method);
    }

    public Object execute(SqlSession sqlSession, Object[] args) {
        Object result = null;
        switch (command.getType()) {
            case INSERT:
                break;
            case DELETE:
                break;
            case UPDATE:
                break;
            case SELECT:
                Object params = method.convertArgsToSqlCommandParam(args);
                result = sqlSession.selectOne(command.getName(), params);
                break;
            default:
                throw new RuntimeException("Unknown execution method for: " + command.getName());
        }
        return result;
    }

    /**
     * SQL 指令
     */
    public static class SqlCommand {

        private final String name;
        private final SqlCommandType type;

        public SqlCommand(Configuration configuration, Class<?> mapperInterface, Method method) {
            String statementName = mapperInterface.getName() + "." + method.getName();
            MappedStatement ms = configuration.getMappedStatement(statementName);
            name = ms.getId();
            type = ms.getSqlCommandType();
        }

        public String getName() {
            return name;
        }

        public SqlCommandType getType() {
            return type;
        }
    }

    /**
     * 方法签名
     */
    public static class MethodSignature {

        private final SortedMap<Integer, String> params;

        public MethodSignature(Configuration configuration, Method method) {
            this.params = Collections.unmodifiableSortedMap(getParams(method));
        }

        public Object convertArgsToSqlCommandParam(Object[] args) {
            final int paramCount = params.size();
            if (args == null || paramCount == 0) {
                // 如果没有参数
                return null;
            } else if (paramCount == 1) {
                return args[params.keySet().iterator().next().intValue()];
            } else {
                // 否则，返回一个 ParamMap，修改参数名，参数名就是其位置
                final Map<String, Object> param = new ParamMap<Object>();
                int i = 0;
                for (Map.Entry<Integer, String> entry : params.entrySet()) {
                    // 1.先加一个 #{0},#{1},#{2} ... 参数
                    param.put(entry.getValue(), args[entry.getKey().intValue()]);
                    // issue #71, add param names as param1, param2...but ensure backward compatibility
                    final String genericParamName = "param" + (i + 1);
                    /*
                     * 再加一个#{param1}, #{param2} ... 参数
                     * 你可以传递多个参数给一个映射器方法。如果这样做了。
                     * 默认情况下他们将会以他们在参数列表中的位置来命令，比如：#{param1},#{param2} 等。
                     * 如果你想改变参数的名称(只在多参数情况下)，那么你可以在参数上使用@Param("paramName") 注解
                     */
                    if (!param.containsKey(genericParamName)) {
                        param.put(genericParamName, args[entry.getKey()]);
                    }
                    i++;
                }
                return param;
            }
        }

        private SortedMap<Integer, String> getParams(Method method) {
            // 用一个TreeMap，这样就能保证还是按参数先后顺序
            final SortedMap<Integer, String> params = new TreeMap<>();
            final Class<?>[] aryTags = method.getParameterTypes();
            for (int i = 0; i < aryTags.length; i++) {
                String paramName = String.valueOf(aryTags[i]);
                params.put(i, paramName);
            }
            return params;
        }
    }

    /**
     * 参数 map 静态内部类，更严格的get方法，如果没有相应的key，报错
     * @param <V>
     */
    public static class ParamMap<V> extends HashMap<String, V> {

        private static final long serialVersionUID = -2212268410512043556L;

        @Override
        public V get(Object key) {
            if (!super.containsKey(key)) {
                throw new RuntimeException("Parameter '" + key + "' not found. Available parameters are " + keySet());
            }
            return super.get(key);
        }
    }
}