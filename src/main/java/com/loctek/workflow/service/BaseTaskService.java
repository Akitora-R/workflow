package com.loctek.workflow.service;

import cn.hutool.core.util.StrUtil;
import com.loctek.workflow.constant.AuditStatus;
import com.loctek.workflow.entity.activiti.AuditStatusDTO;
import com.loctek.workflow.entity.activiti.BaseTaskConclusionDTO;
import com.loctek.workflow.entity.activiti.BaseTaskDTO;
import com.loctek.workflow.entity.activiti.BaseTaskVariable;
import lombok.RequiredArgsConstructor;
import org.activiti.engine.ActivitiObjectNotFoundException;
import org.activiti.engine.HistoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.*;
import org.activiti.engine.task.IdentityLinkType;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskInfo;

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
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(historicActivityInstance.getProcessInstanceId()).singleResult();
        return getDTO(historicTaskInstance, historicProcessInstance.getBusinessKey(), (String) variables.get("applierId"), (Boolean) variables.get("approval"), (String) variables.get("comment"), candidateList);
    }

    public List<BaseTaskDTO<V>> getTaskListByBusinessKey(String businessKey) {
        List<HistoricTaskInstance> historicTaskInstances = historyService.createHistoricTaskInstanceQuery()
                .processInstanceBusinessKey(businessKey).orderByHistoricTaskInstanceStartTime().desc().list();
        return getDTOListByInstanceList(historicTaskInstances);
    }

    public List<BaseTaskDTO<V>> getTaskListByBusinessKeyAndInstanceId(String businessKey,String instId) {
        List<HistoricTaskInstance> historicTaskInstances = historyService.createHistoricTaskInstanceQuery()
                .processInstanceId(instId)
                .processInstanceBusinessKey(businessKey).orderByHistoricTaskInstanceStartTime().desc().list();
        return getDTOListByInstanceList(historicTaskInstances);
    }

    public String getInstIdByTaskId(String taskId){
        return historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult().getProcessInstanceId();
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

    public boolean completeTaskByName(BaseTaskConclusionDTO<V> dto, String name, String businessKey) {
        Task task = taskService.createTaskQuery()
                .taskName(name)
                .processInstanceBusinessKey(businessKey)
                .singleResult();
        if (task == null) {
            return false;
        }
        dto.setTaskId(task.getId());
        return completeTask(dto);
    }

    public Map<String, ? extends AuditStatusDTO> getAuditStatusByBusinessKeyList(List<String> businessKeyList) {
        return businessKeyList.stream().collect(Collectors.toMap(bk -> bk, bk -> {
            BaseTaskDTO<V> lastTask = getLastTaskByBusinessKey(bk);
            boolean notSubmitted = lastTask == null;
            AuditStatus status =
                    notSubmitted ? AuditStatus.notSubmitted :
                            !lastTask.getFinished() ? AuditStatus.pending :
                                    lastTask.getApproval() ? AuditStatus.accepted : AuditStatus.rejected;
            return new AuditStatusDTO(bk,
                    notSubmitted ? null : lastTask.getId(),
                    notSubmitted ? null :lastTask.getName(),
                    status,
                    notSubmitted ? null : lastTask.getCandidateList(),
                    notSubmitted ? null : lastTask.getAssignee());
        }));
    }

    public Map<String, ? extends BaseTaskDTO<V>> getLastTaskByBusinessKeyList(List<String> businessKeyList) {
        HashMap<String, BaseTaskDTO<V>> map = new HashMap<>();
        for (String bk : businessKeyList) {
            map.put(bk, getLastTaskByBusinessKey(bk));
        }
        return map;
    }

    public List<String> getTaskIdList(String businessKey, String taskName) {
        return historyService.createHistoricTaskInstanceQuery()
                .taskNameLike(taskName)
                .processInstanceBusinessKey(businessKey).list().stream().map(TaskInfo::getId).collect(Collectors.toList());
    }

    protected String getAuditDescription(AuditStatus auditStatus, BaseTaskDTO<V> baseTaskDTO) {
        switch (auditStatus) {
            case notSubmitted:
                return "未提交";
            case pending:
                return StrUtil.format("{}中, 审核人[{}]", baseTaskDTO.getName(), String.join(",", baseTaskDTO.getCandidateList()));
            case accepted:
                return "结束";
            case rejected:
                return StrUtil.format("{}不通过, 审核人[{}]", baseTaskDTO.getName(), baseTaskDTO.getAssignee());
            default:
                return null;
        }
    }

    protected BaseTaskDTO<V> getDTO(HistoricTaskInstance historicTaskInstance,
                                    String businessKey,
                                    String applierId,
                                    Boolean approval,
                                    String comment,
                                    List<String> candidateList) {
        BaseTaskDTO<V> dto = new BaseTaskDTO<>();
        dto.setId(historicTaskInstance.getId());
        dto.setBusinessKey(businessKey);
        dto.setName(historicTaskInstance.getName());
        dto.setAssignee(historicTaskInstance.getAssignee());
        dto.setApplierId(applierId);
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
        return historicTaskInstances.stream().map(ht -> {
            Map<String, Object> variables = getVariablesByTaskId(ht.getId());
            List<String> candidateList = getCandidateListByTaskId(ht.getId());
            HistoricProcessInstance hp = historyService.createHistoricProcessInstanceQuery().processInstanceId(ht.getProcessInstanceId()).singleResult();
            return getDTO(ht,
                    hp.getBusinessKey(),
                    (String) variables.get("applierId"),
                    (Boolean) variables.get("approval"),
                    (String) variables.get("comment"),
                    candidateList);
        }).collect(Collectors.toList());
    }
}
