package com.zhi.session;

/**
 * @author zhijianhao
 * @created by zhijianhao on 2023/3/22-22:11
 */
public interface SqlSessionFactory {
    /**
     * 打开一个 session
     *
     * @return SqlSession
     */
    SqlSession openSession();
}
