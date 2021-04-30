package com.loctek.workflow.service;

import cn.hutool.core.util.StrUtil;
import com.loctek.workflow.entity.activiti.BaseTaskConclusionDTO;
import com.loctek.workflow.entity.activiti.BaseTaskDTO;
import com.loctek.workflow.entity.activiti.BaseTaskVariable;
import lombok.RequiredArgsConstructor;
import org.activiti.engine.ActivitiObjectNotFoundException;
import org.activiti.engine.HistoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.*;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.IdentityLinkType;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public abstract class BaseTaskService<V extends BaseTaskVariable> {
    protected final HistoryService historyService;
    protected final TaskService taskService;

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

    public List<String> getCandidateListByTaskId(String taskId) {
        return historyService.getHistoricIdentityLinksForTask(taskId).stream()
                .filter(i -> IdentityLinkType.CANDIDATE.equals(i.getType()))
                .map(HistoricIdentityLink::getUserId).collect(Collectors.toList());
    }

    /**
     * 通过activity获取Task
     *
     * @param activityInstanceId activityInstanceId
     * @return TaskDTO
     */
    public BaseTaskDTO<V> getTaskDTOByActivityInstanceId(String activityInstanceId) {
        HistoricActivityInstance historicActivityInstance = historyService.createHistoricActivityInstanceQuery().activityInstanceId(activityInstanceId).singleResult();
        String taskId = historicActivityInstance.getTaskId();
        if (StrUtil.isBlankOrUndefined(taskId)) {
            return null;
        }
        HistoricTaskInstance historicTaskInstance = historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
        Map<String, Object> variables = this.getVariablesByTaskId(taskId);
        List<String> candidateList = getCandidateListByTaskId(historicTaskInstance.getId());
        return getDTO(historicTaskInstance, (Boolean) variables.get("approval"), (String) variables.get("comment"), candidateList);
    }

    public List<BaseTaskDTO<V>> getTaskListByBusinessKey(String businessKey) {
        List<HistoricTaskInstance> historicTaskInstances = historyService.createHistoricTaskInstanceQuery()
                .processInstanceBusinessKey(businessKey).orderByHistoricTaskInstanceStartTime().desc().list();
        return getDTOListByInstanceList(historicTaskInstances);
    }

    public List<BaseTaskDTO<V>> getTaskListByInstanceId(String instanceId) {
        List<HistoricTaskInstance> historicTaskInstances = historyService.createHistoricTaskInstanceQuery().processInstanceId(instanceId).list();
        return getDTOListByInstanceList(historicTaskInstances);
    }

    public List<BaseTaskDTO<V>> getTaskListByUser(String user, Boolean isFinished) {
        HistoricTaskInstanceQuery query = historyService.createHistoricTaskInstanceQuery()
                .or().taskAssigneeLikeIgnoreCase(user).taskCandidateUser(user).endOr();
        if (isFinished != null) {
            if (isFinished) {
                query.finished();
            } else {
                query.unfinished();
            }
        }
        List<HistoricTaskInstance> historicTaskInstances = query.list();
        return getDTOListByInstanceList(historicTaskInstances);
    }

    public BaseTaskDTO<V> getLastTaskByBusinessKey(String businessKey) {
        List<BaseTaskDTO<V>> taskList = getTaskListByBusinessKey(businessKey);
        return taskList.isEmpty() ? null : taskList.get(0);
    }

    public boolean completeTask(BaseTaskConclusionDTO<V> dto) {
        try {
            taskService.claim(dto.getTaskId(), dto.getUserId());
            taskService.complete(dto.getTaskId(), dto.getVariable().toMap(), true);
            return true;
        } catch (ActivitiObjectNotFoundException ignored) {
            return false;
        }
    }

    protected BaseTaskDTO<V> getDTO(HistoricTaskInstance historicTaskInstance, Boolean approval, String comment, List<String> candidateList) {
        BaseTaskDTO<V> dto = new BaseTaskDTO<>();
        dto.setId(historicTaskInstance.getId());
        dto.setName(historicTaskInstance.getName());
        dto.setAssignee(historicTaskInstance.getAssignee());
        dto.setCreateTime(historicTaskInstance.getCreateTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        dto.setCandidateList(candidateList);
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
            List<String> candidateList = getCandidateListByTaskId(h.getId());
            return getDTO(h, (Boolean) variables.get("approval"), (String) variables.get("comment"), candidateList);
        }).collect(Collectors.toList());
    }
}
