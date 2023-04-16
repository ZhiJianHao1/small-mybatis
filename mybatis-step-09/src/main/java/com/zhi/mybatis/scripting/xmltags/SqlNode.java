package com.zhi.mybatis.scripting.xmltags;

/**
 * sql节点
 * @author zhijianhao
 */
public interface SqlNode {

    boolean apply(DynamicContext context);
}
