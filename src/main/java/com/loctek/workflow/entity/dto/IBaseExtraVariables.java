package com.loctek.workflow.entity.dto;

import java.util.Map;

public interface IBaseExtraVariables {
    Map<String,Object> getTaskExtraVariables();
    Map<String,Object> getInstanceExtraVariables();
}
