package com.loctek.workflow.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.activiti.engine.repository.ProcessDefinition;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProcessDefinitionDTO {
    private String id;
    private String name;
    private String key;
    private Integer version;
    private Boolean isSuspended;

    public ProcessDefinitionDTO(ProcessDefinition processDefinition) {
        this.id = processDefinition.getId();
        this.name = processDefinition.getName();
        this.key = processDefinition.getKey();
        this.version = processDefinition.getVersion();
        this.isSuspended = processDefinition.isSuspended();
    }
}
