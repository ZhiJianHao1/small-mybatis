package com.zhi.mybatis.datasource.pooled;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 池化代理连接
 * @author zhijianhao
 * @created by zhijianhao on 2023/3/26-17:59
 */
public class PooledConnection implements InvocationHandler {
    private static final String CLOSE = "close";

    private static final Class<?>[] IFACES = new Class<?>[]{Connection.class};
    /**
     * hashcode = 0
     */
    private int hashCode = 0;

    /**
     * 有池化数据源
     * PooledDataSource
     */
    private PooledDataSource dataSource;
    /**
     * 真实连接
     */
    private Connection realConnection;
    /**
     * 代理连接
     */
    private Connection proxyConnection;
    /**
     * 检测时间戳
     */
    private long checkoutTimestamp;
    /**
     * 创建时间戳
     */
    private long createdTimestamp;
    /**
     * 最后使用时间戳
     */
    private long lastUsedTimestamp;
    /**
     * 连接类型code
     */
    private int connectionTypeCode;
    /**
     * 是否有效
     */
    private boolean valid;

    /**
     * 有参构造
     * @param connection 连接
     * @param dataSource 数据源
     */
    public PooledConnection(Connection connection, PooledDataSource dataSource) {
        this.hashCode = connection.hashCode();
        this.realConnection = connection;
        this.dataSource = dataSource;
        this.createdTimestamp = System.currentTimeMillis();
        this.lastUsedTimestamp = System.currentTimeMillis();
        this.valid = true;
        /**
         * 代理连接
         * 创建一个动态代理实例 代理Connection接口
         * 第一个参数是用于加载代理类的类加载器
         * 第二参数是代理类需要实现的接口类列表
         * 第三个参数是用于处理代理方法调用的 invocationHandler 对象
         */
        this.proxyConnection = (Connection) Proxy.newProxyInstance(Connection.class.getClassLoader(), IFACES, this);
    }

    /**
     * invoke 会在代理对象的方法被调用时被调用。在 invoke() 方法中，可以实现自定义的处理逻辑，例如记录日志、权限校验、统计调用次数等。
     * 当调用代理对象的方法时，JVM 实际上会调用代理对象的 invoke() 方法，然后在 invoke() 方法中实现自定义的逻辑，并最终返回代理对象方法的执行结果。
     *
     *
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String methodName = method.getName();
        //如果是调用 CLOSE 关闭连接方法，则将连接加入连接池中，并返回null
        if (methodName.hashCode() == CLOSE.hashCode() && CLOSE.equals(methodName)) {
            dataSource.pushConnection(this);
            return null;
        } else {
            if (!Object.class.equals(method.getDeclaringClass())) {
                // 除了toString() 方法，其他方法调用前要检查connection是否还是合法的，不合法要抛出 SQLException
                checkConnection();
            }
            // 其他方法交给connection去掉用
            return method.invoke(realConnection, args);
        }
    }

    /**
     * 除了toString() 方法，其他方法调用前要检查connection是否还是合法的，不合法要抛出 SQLException
     * @throws SQLException
     */
    private void checkConnection() throws SQLException {
        if (!valid) {
            throw new SQLException("Error accessing PooledConnection. Connection is invalid.");
        }
    }

    public void invalidate() {
        valid = false;
    }

    public boolean isValid() {
        return valid && realConnection != null && dataSource.pingConnection(this);
    }

    public Connection getRealConnection() {
        return realConnection;
    }

    public Connection getProxyConnection() {
        return proxyConnection;
    }

    public int getRealHashCode() {
        return realConnection == null ? 0 : realConnection.hashCode();
    }

    public int getConnectionTypeCode() {
        return connectionTypeCode;
    }

    public void setConnectionTypeCode(int connectionTypeCode) {
        this.connectionTypeCode = connectionTypeCode;
    }

    public long getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(long createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    public long getLastUsedTimestamp() {
        return lastUsedTimestamp;
    }

    public void setLastUsedTimestamp(long lastUsedTimestamp) {
        this.lastUsedTimestamp = lastUsedTimestamp;
    }

    public long getTimeElapsedSinceLastUse() {
        return System.currentTimeMillis() - lastUsedTimestamp;
    }

    public long getAge() {
        return System.currentTimeMillis() - createdTimestamp;
    }

    public long getCheckoutTimestamp() {
        return checkoutTimestamp;
    }

    public void setCheckoutTimestamp(long timestamp) {
        this.checkoutTimestamp = timestamp;
    }

    public long getCheckoutTime() {
        return System.currentTimeMillis() - checkoutTimestamp;
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PooledConnection) {
            return realConnection.hashCode() == (((PooledConnection) obj).realConnection.hashCode());
        } else if (obj instanceof Connection) {
            return hashCode == obj.hashCode();
        } else {
            return false;
        }
    }
}
