package com.loctek.workflow.entity.dto;

import cn.hutool.core.date.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.activiti.engine.history.HistoricActivityInstance;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityDTO {
    private String id;
    private String name;
    private String type;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private TaskDTO task;

    private void setActivity(HistoricActivityInstance historicActivityInstance) {
        this.id = historicActivityInstance.getId();
        this.name = historicActivityInstance.getActivityName();
        this.type = historicActivityInstance.getActivityType();
        this.startTime = DateUtil.toLocalDateTime(historicActivityInstance.getStartTime());
        this.endTime = DateUtil.toLocalDateTime(historicActivityInstance.getEndTime());
    }

    public ActivityDTO(HistoricActivityInstance historicActivityInstance, TaskDTO taskDTO) {
        setActivity(historicActivityInstance);
        this.task = taskDTO;
    }
}
