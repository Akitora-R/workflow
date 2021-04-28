package com.loctek.workflow.entity.activiti;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class BaseTaskConclusionDTO<V extends BaseTaskVariable> {
    @NotBlank
    protected String taskId;
    @NotBlank
    protected String userId;
    protected V variable;
}
