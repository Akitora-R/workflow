package com.loctek.workflow.service;

import cn.hutool.core.util.StrUtil;
import com.loctek.workflow.entity.dto.BaseTaskDTO;
import com.loctek.workflow.entity.dto.IBaseExtraTaskVariables;
import lombok.RequiredArgsConstructor;
import org.activiti.engine.HistoryService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;

import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public abstract class ITaskService<V extends IBaseExtraTaskVariables> {
    private final HistoryService historyService;

    /**
     * 通过task获取该task的全部局部变量
     *
     * @param taskId taskId
     * @return 局部变量
     */
    public Map<String, Object> getVariablesByTaskId(String taskId) {
        List<HistoricVariableInstance> historicVariableInstanceList = historyService.createHistoricVariableInstanceQuery().taskId(taskId).list();
        return historicVariableInstanceList.stream()
                .collect(Collectors
                        .toMap(HistoricVariableInstance::getVariableName, HistoricVariableInstance::getValue, (a, b) -> a));
    }

    /**
     * 通过activity获取Task
     *
     * @param activityId activityId
     * @return TaskDTO
     */
    public BaseTaskDTO<V> getTaskByActivityId(String activityId) {
        HistoricActivityInstance historicActivityInstance = historyService.createHistoricActivityInstanceQuery().activityId(activityId).singleResult();
        String taskId = historicActivityInstance.getTaskId();
        if (StrUtil.isBlankOrUndefined(taskId)) {
            return null;
        }
        HistoricTaskInstance historicTaskInstance = historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
        Map<String, Object> variables = this.getVariablesByTaskId(taskId);
        return getDTO(historicTaskInstance, (Boolean) variables.get("approval"), (String) variables.get("comment"));
    }

    public List<BaseTaskDTO<V>> getTaskListByBusinessKey(String businessKey) {
        List<HistoricTaskInstance> historicTaskInstances = historyService.createHistoricTaskInstanceQuery().processInstanceBusinessKey(businessKey).list();
        return getDTOListByInstanceList(historicTaskInstances);
    }

    public List<BaseTaskDTO<V>> getTaskListByInstanceId(String instanceId) {
        List<HistoricTaskInstance> historicTaskInstances = historyService.createHistoricTaskInstanceQuery().processInstanceId(instanceId).list();
        return getDTOListByInstanceList(historicTaskInstances);
    }

    public List<BaseTaskDTO<V>> getTaskListByUser(String user) {
        List<HistoricTaskInstance> historicTaskInstances = historyService.createHistoricTaskInstanceQuery().taskAssigneeLikeIgnoreCase(user).list();
        return getDTOListByInstanceList(historicTaskInstances);
    }

    protected BaseTaskDTO<V> getDTO(HistoricTaskInstance historicTaskInstance, Boolean approval, String comment) {
        BaseTaskDTO<V> dto = new BaseTaskDTO<>();
        dto.setId(historicTaskInstance.getId());
        dto.setName(historicTaskInstance.getName());
        dto.setAssignee(historicTaskInstance.getAssignee());
        dto.setCreateTime(historicTaskInstance.getCreateTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        boolean finished = historicTaskInstance.getEndTime() != null;
        dto.setFinished(finished);
        if (finished) {
            dto.setEndTime(historicTaskInstance.getEndTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
            dto.setApproval(approval);
            dto.setComment(comment);
        }
        return dto;
    }

    protected List<BaseTaskDTO<V>> getDTOListByInstanceList(List<HistoricTaskInstance> historicTaskInstances) {
        return historicTaskInstances.stream().map(h -> {
            Map<String, Object> variables = getVariablesByTaskId(h.getId());
            return getDTO(h, (Boolean) variables.get("approval"), (String) variables.get("comment"));
        }).collect(Collectors.toList());
    }
}
