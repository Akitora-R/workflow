package com.loctek.workflow.entity.activiti;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * 流程需要的额外变量, 自行重写
 */
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseInstanceVariable {
    public String applierId;

    public abstract Map<String, Object> toMap();
}
