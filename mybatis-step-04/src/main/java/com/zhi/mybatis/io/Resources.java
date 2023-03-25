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
        return new InputStreamReader(getResourcesAsStream(resource));
    }

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
