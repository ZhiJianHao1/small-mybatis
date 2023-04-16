package com.zhi.mybatis.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * @author zhijianhao
 * @created by zhijianhao on 2023/3/23-23:13
 */
public class Resources {
    public static Reader getResourcesAsReader(String resource) throws IOException {
        /**
         * 将字节流转换为字符流，常用于读取网络流或文件流中的数据，并将其转换为文本格式。
         */
        return new InputStreamReader(getResourcesAsStream(resource));
    }

    /**
     * 将字节码文件加载到内存中，创建对应的类对象，并提供访问该类的接口。
     * 在 Java 中，ClassLoader 是实现动态加载和模块化编程的关键技术之一。
     * ClassLoader采用双亲委派模型
     * @param resource
     * @return
     * @throws IOException
     */
    /**
     * 1.获取系统的类加起
     * 2.通过classLoader.getResourceAsStream(resource) 获取指定文件(mybatis-config-datasource.xml)的输入流
     */
    public static InputStream getResourcesAsStream(String resource) throws IOException {
        ClassLoader[] classLoad = getClassLoad();
        for (ClassLoader classLoader : classLoad) {
            InputStream resourceAsStream = classLoader.getResourceAsStream(resource);
            if (resourceAsStream != null) {
                return resourceAsStream;
            }
        }
        throw new IOException("Could not find resource " + resource);
    }

    /**
     * 获取系统类加载器
     * @return 返回一个ClassLoader 数组
     */
    private static ClassLoader[] getClassLoad() {
        return new ClassLoader[] {
                ClassLoader.getSystemClassLoader(),
                Thread.currentThread().getContextClassLoader()
        };
    }

    public static Class<?> classForName(String namespace) throws ClassNotFoundException {
        return Class.forName(namespace);
    }
}
