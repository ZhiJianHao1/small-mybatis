package com.zhi.mybatis.scripting.xmltags;

/**
 * 静态文本sql节点
 * @author zhijianhao
 */
public class StaticTextSqlNode implements SqlNode {

    private String text;

    public StaticTextSqlNode(String text) {
        this.text = text;
    }


    @Override
    public boolean apply(DynamicContext context) {
        // 将文本添加context
        context.appendSql(text);
        return true;
    }
}
