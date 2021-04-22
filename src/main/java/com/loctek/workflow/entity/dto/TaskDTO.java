package com.loctek.workflow.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.task.Task;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {
    private String id;
    private String name;
    private String assignee;
    private LocalDateTime createTime;
    private LocalDateTime endTime;
    private Boolean finished;
    private Map<String, Object> variable;

    public TaskDTO(Task task, Map<String, Object> variable) {
        this.id = task.getId();
        this.name = task.getName();
        this.assignee = task.getAssignee();
        this.createTime = task.getCreateTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        this.finished = false;
        this.variable = variable;
    }

    public TaskDTO(HistoricTaskInstance historicTaskInstance, Map<String, Object> variable) {
        this.id = historicTaskInstance.getId();
        this.name = historicTaskInstance.getName();
        this.assignee = historicTaskInstance.getAssignee();
        this.createTime = historicTaskInstance.getCreateTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        this.finished = historicTaskInstance.getEndTime() != null;
        this.endTime = this.finished ? historicTaskInstance.getEndTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime() : null;
        this.variable = variable;
    }
}
