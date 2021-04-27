package com.loctek.workflow.entity.activiti;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class BaseTaskConclusionDTO<V extends IBaseExtraTaskVariables> {
    @NotBlank
    protected String taskId;
    @NotNull
    protected Boolean approval;
    @NotBlank
    protected String userId;
    protected String comment;
    protected V extraVariables;
}
