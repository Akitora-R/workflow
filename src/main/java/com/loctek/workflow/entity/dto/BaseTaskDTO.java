package com.loctek.workflow.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.activiti.engine.history.HistoricTaskInstance;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseTaskDTO<T extends IBaseExtraVariables> {
    String id;
    String name;
    String assignee;
    LocalDateTime createTime;
    LocalDateTime endTime;
    Boolean finished;
    Boolean approval;
    String comment;
    T extraVariables;

    private void initBaseEntity(HistoricTaskInstance historicTaskInstance, Boolean approval, String comment){
        this.id = historicTaskInstance.getId();
        this.name = historicTaskInstance.getName();
        this.assignee = historicTaskInstance.getAssignee();
        this.createTime = historicTaskInstance.getCreateTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        this.finished = historicTaskInstance.getEndTime() != null;
        this.endTime = this.finished ? historicTaskInstance.getEndTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime() : null;
        this.approval = approval;
        this.comment = comment;
    }

    public BaseTaskDTO(HistoricTaskInstance historicTaskInstance, Boolean approval, String comment, T extraVariables) {
        initBaseEntity(historicTaskInstance, approval, comment);
        this.extraVariables = extraVariables;
    }
    public BaseTaskDTO(HistoricTaskInstance historicTaskInstance, Map<String,Object> variables, T extraVariables) {
        initBaseEntity(historicTaskInstance, (Boolean) variables.get("approval"), (String) variables.get("comment"));
        this.extraVariables = extraVariables;
    }
}
