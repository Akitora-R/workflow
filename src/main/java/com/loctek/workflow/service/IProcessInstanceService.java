package com.loctek.workflow.service;

import com.loctek.workflow.entity.dto.BaseProcessInstanceDTO;
import com.loctek.workflow.entity.dto.BaseProcessInstanceInitBO;
import com.loctek.workflow.entity.dto.IBaseExtraInstanceVariables;
import lombok.RequiredArgsConstructor;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.runtime.ProcessInstance;

import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public abstract class IProcessInstanceService<V extends IBaseExtraInstanceVariables> {
    private final RuntimeService runtimeService;
    private final HistoryService historyService;

    /**
     * 通过processInstance获取该processInstance的全局变量
     *
     * @param processInstanceId processInstanceId
     * @return 全局变量
     */
    public Map<String, Object> getVariablesByInstanceId(String processInstanceId){
        List<HistoricVariableInstance> historicVariableInstanceList = historyService.createHistoricVariableInstanceQuery().processInstanceId(processInstanceId).list();
        return historicVariableInstanceList.stream()
                .collect(Collectors
                        .toMap(HistoricVariableInstance::getVariableName, HistoricVariableInstance::getValue, (a,b)->a));
    }

    /**
     * 获取全部活动中的ProcessInstance
     *
     * @return 活动中的ProcessInstance
     */
    public List<BaseProcessInstanceDTO<V>> getActiveProcessInstanceList(){
        List<ProcessInstance> processInstanceList = runtimeService.createProcessInstanceQuery().processDefinitionKey(getDefinitionKey()).active().list();
        return processInstanceList.stream().map(p -> getDTO(p, convertInstanceVariable(getVariablesByInstanceId(p.getProcessInstanceId()))))
                .collect(Collectors.toList());
    }

    /**
     * 按bk获取ProcessInstance
     * @param businessKey businessKey
     *
     * @return ProcessInstance
     */
    public BaseProcessInstanceDTO<V> getProcessInstanceByBusinessKey(String businessKey){
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .processDefinitionKey(getDefinitionKey()).processInstanceBusinessKey(businessKey).active().singleResult();
        return getDTO(processInstance, convertInstanceVariable(getVariablesByInstanceId(processInstance.getProcessInstanceId())));
    }

    /**
     * 以businessKey开始流程
     *
     * @param initDTO 开始信息
     * @return 流程实例
     */
    public BaseProcessInstanceDTO<V> startProcessInstance(BaseProcessInstanceInitBO<V> initDTO){
        ProcessInstance processInstance = runtimeService
                .startProcessInstanceByKey(initDTO.getProcessDefinitionKey(), initDTO.getBusinessKey(), initDTO.getExtraVariables().getVariables());
        return getDTO(processInstance, initDTO.getExtraVariables());
    }

    /**
     * 以流程id删除流程
     *
     * @param procId 流程id
     * @param deleteReason 删除原因
     */
    public void deleteProcessInstance(String procId,String deleteReason){
        runtimeService.deleteProcessInstance(procId, deleteReason);
    }

    private BaseProcessInstanceDTO<V> getDTO(ProcessInstance processInstance, V variables) {
        BaseProcessInstanceDTO<V> dto = new BaseProcessInstanceDTO<>();
        dto.setId(processInstance.getId());
        dto.setName(processInstance.getName());
        dto.setDefinitionKey(processInstance.getProcessDefinitionKey());
        dto.setDefinitionName(processInstance.getProcessDefinitionName());
        dto.setBusinessKey(processInstance.getBusinessKey());
        dto.setStartTime(processInstance.getStartTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        dto.setExtraVariables(variables);
        return dto;
    }

    protected abstract V convertInstanceVariable(Map<String, Object> variables);

    protected abstract String getDefinitionKey();
}
