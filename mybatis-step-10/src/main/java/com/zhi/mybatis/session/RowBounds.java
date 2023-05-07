package com.zhi.mybatis.session;

/**
 * 分页记录限制
 * @author zhijianhao
 * @created by zhijianhao on 2023/5/3-16:22
 */
public class RowBounds {

    public static final int NO_ROW_OFFSET = 0;
    public static final int NO_ROW_LIMIT = Integer.MAX_VALUE;
    public static final RowBounds DEFAULT = new RowBounds();

    // offset,limit 就等于一般分页的start，limit
    private int offset;

    private int limit;

    // 默认是一页Integer.MAX_VALUE条
    public RowBounds() {
        this.offset = NO_ROW_OFFSET;
        this.limit = NO_ROW_LIMIT;
    }

    public int getOffset() {
        return offset;
    }

    public int getLimit() {
        return limit;
    }
}
