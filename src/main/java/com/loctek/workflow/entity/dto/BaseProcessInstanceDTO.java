package com.loctek.workflow.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.runtime.ProcessInstance;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseProcessInstanceDTO<T extends IBaseExtraVariables> {
    String id;
    String name;
    String definitionKey;
    String definitionName;
    String businessKey;
    LocalDateTime startTime;
    LocalDateTime endTime;
    T extraVariables;

    private void initBaseEntity(ProcessInstance processInstance) {
        this.id = processInstance.getId();
        this.name = processInstance.getName();
        this.definitionKey = processInstance.getProcessDefinitionKey();
        this.definitionName = processInstance.getProcessDefinitionName();
        this.businessKey = processInstance.getBusinessKey();
        this.startTime = processInstance.getStartTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    private void initBaseEntity(HistoricProcessInstance processInstance) {
        this.id = processInstance.getId();
        this.name = processInstance.getName();
        this.definitionKey = processInstance.getProcessDefinitionKey();
        this.definitionName = processInstance.getProcessDefinitionName();
        this.businessKey = processInstance.getBusinessKey();
        this.startTime = processInstance.getStartTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        this.endTime = processInstance.getEndTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public BaseProcessInstanceDTO(ProcessInstance processInstance) {
        initBaseEntity(processInstance);
    }

    public BaseProcessInstanceDTO(ProcessInstance processInstance, T extraVariables) {
        initBaseEntity(processInstance);
        this.extraVariables = extraVariables;
    }

    public BaseProcessInstanceDTO(HistoricProcessInstance processInstance) {
        initBaseEntity(processInstance);
    }

    public BaseProcessInstanceDTO(HistoricProcessInstance processInstance, T extraVariables) {
        initBaseEntity(processInstance);
        this.extraVariables = extraVariables;
    }
}
