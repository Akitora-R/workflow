package com.loctek.workflow.entity.activiti;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * 流程需要的额外变量, 自行重写
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public abstract class BaseInstanceVariable {
    protected String applierId;
    protected String applierDepartmentId;

    public abstract Map<String, Object> toMap();
}
