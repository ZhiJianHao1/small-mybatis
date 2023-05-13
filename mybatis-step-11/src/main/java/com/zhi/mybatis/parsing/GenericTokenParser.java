package com.zhi.mybatis.parsing;

/**
 * 普通记号解析器，处理#{}和${}参数
 * @author zhijianhao
 */
public class GenericTokenParser {

    // 有一个开始和结束的记号
    private final String openToken;

    private final String closeToken;
    // 记号处理器
    private final TokenHandler handler;

    public GenericTokenParser(String openToken, String closeToken, TokenHandler tokenHandler) {
        this.openToken = openToken;
        this.closeToken = closeToken;
        this.handler = tokenHandler;
    }

    public String parse(String text) {
        StringBuilder builder = new StringBuilder();
        if (text != null && text.length() > 0) {
            char[] src = text.toCharArray();
            int offset = 0;
            int start = text.indexOf(openToken, offset);
            // #{favouriteSection, jdbcType=VARCHAR}
            // 这里是循环解析参数，参考GenericTokenParserTest, 比如可以解析${first_name} ${last_name} reporting. 这样的字符传，里面有3个${}
            while (start > -1) {
                // 判断下 ${ 前面是否是反斜杠，这个逻辑在老版的mybatis中是没有 (3.1.0) }
                if (start > 0 && src[start - 1] == '\\') {
                    // the variable is escaped. remove the backslash.
                    // 新版已经没有调用 substring 了，改为调用如下的 offset 方式，提高了效率
                    builder.append(src, offset, start - offset - 1).append(openToken);
                    offset = start + openToken.length();
                } else {
                    int end = text.indexOf(closeToken, start);
                    if (end == -1) {
                        builder.append(src, offset, src.length - offset);
                        offset = src.length;
                    } else {
                        builder.append(src, offset, start - offset);
                        offset = start + openToken.length();
                        String content = new String(src, offset, end - offset);
                        // 得到一对大括号里面的字符串后，调用 handler.handlerToken, 比如替换变量这种功能。
                        builder.append(handler.handlerToken(content));
                        offset = end + closeToken.length();
                    }
                }
                start = text.indexOf(openToken, offset);
            }
            if (offset < src.length) {
                builder.append(src, offset, src.length - offset);
            }
        }
        return builder.toString();
    }
}
