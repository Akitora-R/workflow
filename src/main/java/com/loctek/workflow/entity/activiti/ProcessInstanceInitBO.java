package com.loctek.workflow.entity.activiti;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcessInstanceInitBO<T extends BaseInstanceVariable> {
    String processDefinitionKey;
    String businessKey;
    T variables;
}
