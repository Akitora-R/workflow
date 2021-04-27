package com.loctek.workflow.entity.activiti.impl;

import com.loctek.workflow.entity.activiti.BaseProcessInstanceInitDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
public class LeaveProcessInstanceInitDTO extends BaseProcessInstanceInitDTO {
    @NotBlank
    String applierGroup;
    @NotNull
    Integer applierLevel;
    @NotNull
    Double days;
}
