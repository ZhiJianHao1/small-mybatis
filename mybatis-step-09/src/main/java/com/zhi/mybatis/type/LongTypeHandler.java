package com.zhi.mybatis.type;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * TODO
 * Long 类型处理器
 * @author：zhijianhao
 * @date: 2023/4/15
 */
public class LongTypeHandler extends BaseTypeHandler<Long> {


    @Override
    protected void setNonNullParameter(PreparedStatement ps, int i, Long parameter, JdbcType jdbcType) throws SQLException {
        ps.setLong(i, parameter);
    }
}
