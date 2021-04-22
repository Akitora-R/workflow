package com.loctek.workflow.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.activiti.engine.runtime.ProcessInstance;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProcessInstanceDTO {
    private String id;
    private String name;
    private String definitionKey;
    private String definitionName;
    private String businessKey;
    private LocalDateTime startTime;

    public ProcessInstanceDTO(ProcessInstance processInstance) {
        this.id = processInstance.getId();
        this.name = processInstance.getName();
        this.definitionKey = processInstance.getProcessDefinitionKey();
        this.definitionName = processInstance.getProcessDefinitionName();
        this.businessKey = processInstance.getBusinessKey();
        this.startTime = processInstance.getStartTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}
