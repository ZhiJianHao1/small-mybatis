package com.zhi.mybatis.type;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * TODO
 * String 类型处理器
 * @author：zhijianhao
 * @date: 2023/4/15
 */
public class StringTypeHandler extends BaseTypeHandler<String> {

    @Override
    protected void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter);
    }
}
