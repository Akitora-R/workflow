package com.loctek.workflow.entity.activiti;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseActivityDTO<T extends BaseTaskVariable> {
    String id;
    String name;
    String type;
    LocalDateTime startTime;
    LocalDateTime endTime;
    BaseTaskDTO<T> task;
}
