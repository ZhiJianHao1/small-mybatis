package com.zhi.mybatis.scripting.xmltags;

import java.util.List;

/**
 * 混合sql节点
 * @author zhijianhao
 */
public class MixedSqlNode implements SqlNode {

    // 组合模式，拥有一个SqlNode的List
    private List<SqlNode> contents;

    public MixedSqlNode(List<SqlNode> contents) {
        this.contents = contents;
    }

    @Override
    public boolean apply(DynamicContext context) {
        contents.forEach(node -> node.apply(context));
        return true;
    }
}
