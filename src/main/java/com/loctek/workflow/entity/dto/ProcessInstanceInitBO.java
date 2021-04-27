package com.loctek.workflow.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcessInstanceInitBO<T extends IBaseExtraInstanceVariables> {
    String processDefinitionKey;
    String businessKey;
    String applier;
    T extraVariables;
}
