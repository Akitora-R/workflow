package com.loctek.workflow.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;
import java.util.Map;

/**
 * 流程需要的额外变量, 自行重写
 */
public interface IBaseExtraInstanceVariables {
    Map<String,Object> getVariables();
    @JsonIgnore
    List<String> getVariableFields();
}
