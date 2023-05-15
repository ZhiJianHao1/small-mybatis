package com.zhi.mybatis.builder;

import com.zhi.mybatis.mapping.ResultMap;
import com.zhi.mybatis.mapping.ResultMapping;

import java.util.List;

/**
 * TODO
 * 结果映射解析器
 * @author：zhijianhao
 * @date: 2023/5/13
 */
public class ResultMapResolver {

    private final MapperBuilderAssistant assistant;
    private String id;
    private Class<?> type;
    private List<ResultMapping> resultMappings;

    public ResultMapResolver(MapperBuilderAssistant assistant, String id, Class<?> type, List<ResultMapping> resultMappings) {
        this.assistant = assistant;
        this.id = id;
        this.type = type;
        this.resultMappings = resultMappings;
    }

    public ResultMap resolve() {
        return assistant.addResultMap(this.id, this.type, this.resultMappings);
    }
}
