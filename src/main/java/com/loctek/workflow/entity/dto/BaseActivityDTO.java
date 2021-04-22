package com.loctek.workflow.entity.dto;

import cn.hutool.core.date.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.activiti.engine.history.HistoricActivityInstance;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseActivityDTO<T extends IBaseExtraVariables> {
    String id;
    String name;
    String type;
    LocalDateTime startTime;
    LocalDateTime endTime;
    BaseTaskDTO<T> task;

    private void setActivity(HistoricActivityInstance historicActivityInstance) {
        this.id = historicActivityInstance.getId();
        this.name = historicActivityInstance.getActivityName();
        this.type = historicActivityInstance.getActivityType();
        this.startTime = DateUtil.toLocalDateTime(historicActivityInstance.getStartTime());
        this.endTime = DateUtil.toLocalDateTime(historicActivityInstance.getEndTime());
    }

    public BaseActivityDTO(HistoricActivityInstance historicActivityInstance, BaseTaskDTO<T> taskDTO) {
        setActivity(historicActivityInstance);
        this.task = taskDTO;
    }
}
