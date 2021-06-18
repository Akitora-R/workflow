package com.loctek.workflow.entity.activiti;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class BaseTaskConclusionDTO<V extends BaseTaskVariable> {
    protected String taskId;
    @NotBlank
    protected String userId;
    protected V variable;
}
