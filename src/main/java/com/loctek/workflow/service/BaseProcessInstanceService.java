package com.loctek.workflow.service;

import com.loctek.workflow.entity.activiti.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.ActivitiObjectNotFoundException;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;

import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
public abstract class BaseProcessInstanceService<V extends BaseInstanceVariable> {
    protected final RuntimeService runtimeService;
    protected final HistoryService historyService;
    protected final RepositoryService repositoryService;

    public ProcessDefinitionDTO getProcessDefinition() {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionKey(getDefinitionKey()).singleResult();
        return new ProcessDefinitionDTO(
                processDefinition.getId(),
                processDefinition.getName(),
                processDefinition.getKey(),
                processDefinition.getVersion(),
                processDefinition.isSuspended());
    }

    /**
     * 通过processInstance获取该processInstance的全局变量
     *
     * @param processInstanceId processInstanceId
     * @return 全局变量
     */
    public Map<String, Object> getVariablesByInstanceId(String processInstanceId) {
        List<HistoricVariableInstance> historicVariableInstanceList = historyService.createHistoricVariableInstanceQuery().processInstanceId(processInstanceId).list();
        return historicVariableInstanceList.stream()
                .collect(Collectors
                        .toMap(HistoricVariableInstance::getVariableName, HistoricVariableInstance::getValue, (a, b) -> a));
    }

    /**
     * 获取全部活动中的ProcessInstance
     *
     * @return 活动中的ProcessInstance
     */
    public List<BaseProcessInstanceDTO<V>> getActiveProcessInstanceList() {
        List<ProcessInstance> processInstanceList = runtimeService.createProcessInstanceQuery().processDefinitionKey(getDefinitionKey()).active().list();
        return processInstanceList.stream().map(p -> getDTO(p, convertInstanceVariable(getVariablesByInstanceId(p.getProcessInstanceId()))))
                .collect(Collectors.toList());
    }

    /**
     * 按bk获取ProcessInstance
     *
     * @param businessKey businessKey
     * @return ProcessInstance
     */
    public BaseProcessInstanceDTO<V> getProcessInstanceByBusinessKey(String businessKey) {
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .processDefinitionKey(getDefinitionKey()).processInstanceBusinessKey(businessKey).active().singleResult();
        return processInstance == null ? null : getDTO(processInstance, convertInstanceVariable(getVariablesByInstanceId(processInstance.getProcessInstanceId())));
    }

    /**
     * 以businessKey开始流程
     *
     * @param initDTO 开始信息
     * @return 流程实例
     */
    public BaseProcessInstanceDTO<V> startProcessInstance(ProcessInstanceInitBO<V> initDTO) {
        V variables = initDTO.getVariables();
        ProcessInstance processInstance = runtimeService
                .startProcessInstanceByKey(initDTO.getProcessDefinitionKey(), initDTO.getBusinessKey(), variables.toMap());
        return getDTO(processInstance, variables);
    }

    /**
     * 以流程id删除流程
     *
     * @param procId       流程id
     * @param deleteReason 删除原因
     */
    public boolean deleteProcessInstance(String procId, String deleteReason) {
        try {
            runtimeService.deleteProcessInstance(procId, deleteReason);
            return true;
        } catch (ActivitiObjectNotFoundException ignore) {
            log.warn("id: {},删除失败", procId);
        }
        return false;
    }

    private BaseProcessInstanceDTO<V> getDTO(ProcessInstance processInstance, V variables) {
        BaseProcessInstanceDTO<V> dto = new BaseProcessInstanceDTO<>();
        dto.setId(processInstance.getId());
        dto.setName(processInstance.getName());
        dto.setDefinitionKey(processInstance.getProcessDefinitionKey());
        dto.setDefinitionName(processInstance.getProcessDefinitionName());
        dto.setBusinessKey(processInstance.getBusinessKey());
        dto.setStartTime(processInstance.getStartTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        dto.setVariable(variables);
        return dto;
    }

    protected abstract V convertInstanceVariable(Map<String, Object> variables);

    public abstract String getDefinitionKey();
}
