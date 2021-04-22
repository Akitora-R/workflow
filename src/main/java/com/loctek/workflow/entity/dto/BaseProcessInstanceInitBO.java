package com.loctek.workflow.entity.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BaseProcessInstanceInitBO<T extends IBaseExtraVariables> {
    String processDefinitionKey;
    String businessKey;
    String applier;
    T extraVariables;

    public BaseProcessInstanceInitBO(String processDefinitionKey, String businessKey, String applier, T extraVariables) {
        this.processDefinitionKey = processDefinitionKey;
        this.businessKey = businessKey;
        this.applier = applier;
        this.extraVariables = extraVariables;
    }
}
