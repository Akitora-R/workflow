package com.loctek.workflow.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;
import java.util.Map;

/**
 * 任务需要的额外变量, 自行重写
 */
public interface IBaseExtraTaskVariables {
	Map<String,Object> getVariables();
	@JsonIgnore
	List<String> getVariableFields();
}
